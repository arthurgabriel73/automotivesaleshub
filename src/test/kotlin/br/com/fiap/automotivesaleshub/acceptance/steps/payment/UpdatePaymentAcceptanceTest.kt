package br.com.fiap.automotivesaleshub.acceptance.steps.payment

import br.com.fiap.automotivesaleshub.adapters.driven.persistence.payment.PaymentRepositoryAdapter
import br.com.fiap.automotivesaleshub.core.domain.payment.models.Payment
import br.com.fiap.automotivesaleshub.core.domain.payment.valueObjects.OrderId
import br.com.fiap.automotivesaleshub.core.domain.payment.valueObjects.PaymentId
import br.com.fiap.automotivesaleshub.core.domain.payment.valueObjects.PaymentStatus
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.time.Instant
import java.util.*

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class UpdatePaymentAcceptanceTest(private val paymentRepositoryAdapter: PaymentRepositoryAdapter) {

    private val paymentUpdateWebhookUrl = "/v1/webhook/payments"

    @Autowired private lateinit var testRestTemplate: TestRestTemplate

    private lateinit var response: ResponseEntity<String>
    private lateinit var requestInput: Map<String, Any>
    private lateinit var orderId: String

    companion object {
        @Container @ServiceConnection val postgres = PostgreSQLContainer("postgres:16.3")
    }

    @Given("the system has a pending payment")
    fun `the system has a pending payment`() {
        orderId = UUID.randomUUID().toString()
        val payment =
            Payment(
                paymentId = PaymentId(UUID.randomUUID()),
                status = PaymentStatus.PENDING,
                orderId = OrderId(UUID.fromString(orderId)),
                createdAt = Instant.now(),
                updatedAt = Instant.now(),
            )
        paymentRepositoryAdapter.create(payment)
    }

    @Given("the external payment service has a valid payment update")
    fun `the external payment service has a valid payment update`() {
        requestInput = mapOf("resource" to "https://api.mercadopago.com", "topic" to "payment")
    }

    @When("the external payment service submits the payment update")
    fun `the external payment service submits the payment update`() {
        response =
            testRestTemplate.postForEntity(
                "$paymentUpdateWebhookUrl/${orderId}",
                requestInput,
                String::class.java,
            )
    }

    @Then("the payment status should be updated successfully")
    fun `the payment status should be updated successfully`() {
        response.statusCode shouldBe HttpStatus.OK
    }

    @Given("the external payment service has an invalid form to update the payment status")
    fun `the external payment service attempts to update the payment status with invalid data`() {
        requestInput = mapOf("resource" to "https://api.mercadopago.com", "topic" to "invalid")
    }

    @Then("the system should reject the payment update with a bad request error")
    fun `the system should reject the payment update with a bad request error`() {
        response.statusCode shouldBe HttpStatus.BAD_REQUEST
    }

    @Given("the external payment service attempts to update a non-existent payment")
    fun `the external payment service attempts to update a non-existent payment`() {
        orderId =
            UUID.randomUUID().toString() // Use a random UUID that does not exist in the database
        requestInput = mapOf("resource" to "https://api.mercadopago.com", "topic" to "payment")
    }

    @Then("the system should reject the payment update with a not found error")
    fun `the system should reject the payment update with a not found error`() {
        response.statusCode shouldBe HttpStatus.NOT_FOUND
    }
}
