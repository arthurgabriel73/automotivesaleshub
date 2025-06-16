package br.com.fiap.automotivesaleshub.acceptance.steps.vehicle

import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
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
class UpdateVehicleAcceptanceTest {
    private val vehiclesUrl = "/v1/vehicles"

    @Autowired private lateinit var testRestTemplate: TestRestTemplate

    private lateinit var response: ResponseEntity<String>
    private lateinit var requestInput: Map<String, Any>
    private lateinit var vehicleId: String

    companion object {
        @Container @ServiceConnection val postgres = PostgreSQLContainer("postgres:16.3")
    }

    @Given("the admin have registered a vehicle in the system")
    fun `the admin have registered a vehicle in the system`() {
        val input =
            mapOf(
                "make" to "Fiat",
                "model" to "Uno",
                "version" to "Way",
                "yearFabrication" to "2020",
                "yearModel" to "2021",
                "kilometers" to 15000,
                "color" to "Red",
                "plate" to "ABC-1234",
                "price" to 30000L,
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

    @Given("the admin has a valid vehicle update form")
    fun `the admin has a valid vehicle update form`() {
        requestInput =
            mapOf(
                "vehicleId" to vehicleId,
                "plate" to "ABC-1B34", // Changed plate
                "price" to 28000, // Changed price
                "priceCurrency" to "BRL",
                "make" to "Fiat",
                "model" to "Uno",
                "version" to "Way",
                "yearFabrication" to "2020",
                "yearModel" to "2021",
                "kilometers" to 15000L,
                "color" to "Blue", // Changed color
                "status" to "AVAILABLE",
            )
    }

    @When("the admin submits the vehicle update form")
    fun `the admin submits the vehicle update form`() {
        response =
            testRestTemplate.exchange(
                "$vehiclesUrl/$vehicleId",
                HttpMethod.PUT,
                HttpEntity(requestInput),
                String::class.java,
            )
    }

    @Given("the admin has a vehicle update form for a non-existent vehicle")
    fun `the admin has a vehicle update form for a non-existent vehicle`() {
        vehicleId = UUID.randomUUID().toString()
        requestInput =
            mapOf(
                "vehicleId" to vehicleId,
                "plate" to "XYZ-9876",
                "price" to 25000L,
                "priceCurrency" to "BRL",
                "make" to "Toyota",
                "model" to "Corolla",
                "version" to "XEi",
                "yearFabrication" to "2022",
                "yearModel" to "2023",
                "kilometers" to 5000,
                "color" to "White",
                "status" to "AVAILABLE",
            )
    }

    @Then("the vehicle should be updated successfully")
    fun `the vehicle should be updated successfully`() {
        response.statusCode shouldBe HttpStatus.OK
    }

    @Given("the admin has an invalid vehicle update form with incorrect data")
    fun `the admin has an invalid vehicle update form with incorrect data`() {
        vehicleId = UUID.randomUUID().toString()
        requestInput =
            mapOf(
                "vehicleId" to vehicleId,
                "make" to "Fiat",
                "model" to "Uno",
                // Missing required fields like plate, price, etc.
            )
    }

    @Then("the system should reject the vehicle update with a bad request error")
    fun `the system should reject the vehicle update with a bad request error`() {
        response.statusCode shouldBe HttpStatus.BAD_REQUEST
    }

    @Then("the system should reject the vehicle update with a not found error")
    fun `the system should reject the vehicle update with a not found error`() {
        response.statusCode shouldBe HttpStatus.NOT_FOUND
    }
}
