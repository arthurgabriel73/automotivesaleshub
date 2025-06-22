package br.com.fiap.automotivesaleshub.acceptance.steps.vehicle

import io.cucumber.java.en.And
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
import java.util.*

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class PurchaseVehicleAcceptanceTest {
    private val vehiclesUrl = "/v1/vehicles"

    @Autowired private lateinit var testRestTemplate: TestRestTemplate

    private lateinit var response: ResponseEntity<String>
    private lateinit var requestInput: Map<String, Any>
    private lateinit var vehicleId: String

    companion object {
        @Container @ServiceConnection val postgres = PostgreSQLContainer("postgres:16.3")
    }

    @Given("the system has a valid vehicle available for purchase")
    fun `the system has a valid vehicle available for purchase`() {
        val input =
            mapOf(
                "make" to "Fiat",
                "model" to "Argo",
                "version" to "HGT",
                "yearFabrication" to "2020",
                "yearModel" to "2021",
                "kilometers" to 0,
                "color" to "Black",
                "plate" to "HBD-8787",
                "price" to 44000L,
                "priceCurrency" to "BRL",
                "status" to "AVAILABLE",
            )
        vehicleId =
            testRestTemplate
                .postForEntity(vehiclesUrl, input, String::class.java)
                .body
                .toString()
                .substringAfterLast("vehicleId\":\"")
                .substringBefore("\"")
    }

    @And("the sales representative has a valid vehicle purchase request form")
    fun `the sales representative has a valid vehicle purchase request form`() {
        requestInput = mapOf("vehicleId" to vehicleId, "orderId" to UUID.randomUUID().toString())
    }

    @When("the sales representative requests the purchase of the vehicle")
    fun `the sales representative requests the purchase of the vehicle`() {
        response =
            testRestTemplate.postForEntity(
                "$vehiclesUrl/purchase",
                requestInput,
                String::class.java,
            )
    }

    @Then("the vehicle purchase should be accepted")
    fun `the vehicle purchase should be accepted`() {
        response.statusCode shouldBe HttpStatus.ACCEPTED
    }

    @Given("the system has a vehicle that has already been sold")
    fun `the system has a vehicle that has already been sold`() {
        val input =
            mapOf(
                "make" to "Fiat",
                "model" to "Argo",
                "version" to "HGT",
                "yearFabrication" to "2020",
                "yearModel" to "2021",
                "kilometers" to 0,
                "color" to "Black",
                "plate" to "HBD-8787",
                "price" to 44000L,
                "priceCurrency" to "BRL",
                "status" to "SOLD",
            )
        vehicleId =
            testRestTemplate
                .postForEntity(vehiclesUrl, input, String::class.java)
                .body
                .toString()
                .substringAfterLast("vehicleId\":\"")
                .substringBefore("\"")
    }

    @When("the sales representative attempts to request the purchase of the sold vehicle")
    fun `the sales representative attempts to request the purchase of the sold vehicle`() {
        requestInput = mapOf("vehicleId" to vehicleId, "orderId" to UUID.randomUUID().toString())
        response =
            testRestTemplate.postForEntity(
                "$vehiclesUrl/purchase",
                requestInput,
                String::class.java,
            )
    }

    @Then("the system should reject the request with a not acceptable error")
    fun `the system should reject the request with a not acceptable error`() {
        response.statusCode shouldBe HttpStatus.NOT_ACCEPTABLE
    }

    @And(
        "the sales representative has an invalid vehicle purchase request form missing required fields"
    )
    fun `the sales representative has an invalid vehicle purchase request form missing required fields`() {
        requestInput = mapOf("vehicleId" to vehicleId) // Missing orderId
    }

    @When("the sales representative submits the vehicle purchase request form")
    fun `the sales representative submits the vehicle purchase request form`() {
        response =
            testRestTemplate.postForEntity(
                "$vehiclesUrl/purchase",
                requestInput,
                String::class.java,
            )
    }

    @Then("the system should reject the request with a bad request error")
    fun `the system should reject the request with a bad request error`() {
        response.statusCode shouldBe HttpStatus.BAD_REQUEST
    }
}
