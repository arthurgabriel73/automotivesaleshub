package br.com.fiap.automotivesaleshub.acceptance.steps.payment

import br.com.fiap.automotivesaleshub.adapters.driven.persistence.VehicleRepositoryAdapter
import br.com.fiap.automotivesaleshub.adapters.driven.persistence.payment.PaymentRepositoryAdapter
import br.com.fiap.automotivesaleshub.core.domain.payment.models.Payment
import br.com.fiap.automotivesaleshub.core.domain.payment.valueObjects.OrderId
import br.com.fiap.automotivesaleshub.core.domain.payment.valueObjects.PaymentId
import br.com.fiap.automotivesaleshub.core.domain.payment.valueObjects.PaymentStatus
import br.com.fiap.automotivesaleshub.core.domain.vehicle.models.Vehicle
import br.com.fiap.automotivesaleshub.core.domain.vehicle.valueObjects.*
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

    @Autowired private lateinit var vehicleRepositoryAdapter: VehicleRepositoryAdapter
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
        val vehicleId = VehicleId(UUID.randomUUID())
        val vehicle =
            Vehicle(
                vehicleId = vehicleId,
                specifications =
                    Specifications(
                        make = "Fiat",
                        model = "Uno",
                        version = "Way",
                        yearFabrication = "2020",
                        yearModel = "2021",
                        kilometers = 15000,
                        color = "Red",
                    ),
                plate = Plate(plate = "ABC-4356"),
                price = Price(amount = 30000L, currency = PriceCurrency.BRL),
                status = VehicleStatus.PROCESSING,
                createdAt = Instant.now(),
            )
        vehicleRepositoryAdapter.create(vehicle)
        orderId = UUID.randomUUID().toString()
        val payment =
            Payment(
                paymentId = PaymentId(UUID.randomUUID()),
                status = PaymentStatus.PENDING,
                orderId = OrderId(UUID.fromString(orderId)),
                vehicleId = vehicleId,
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
