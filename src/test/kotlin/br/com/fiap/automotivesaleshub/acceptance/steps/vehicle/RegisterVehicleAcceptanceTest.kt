package br.com.fiap.automotivesaleshub.acceptance.steps.vehicle

import br.com.fiap.automotivesaleshub.adapters.driven.api.VehicleSalesServiceApi
import br.com.fiap.automotivesaleshub.adapters.driven.api.dto.response.GenerateQRCodeResponse
import br.com.fiap.automotivesaleshub.adapters.driven.api.dto.response.NotifyPaymentResponse
import br.com.fiap.automotivesaleshub.adapters.driven.api.dto.response.SaveVehicleResponse
import br.com.fiap.automotivesaleshub.adapters.driven.api.dto.response.UpdateVehicleResponse
import br.com.fiap.automotivesaleshub.adapters.driven.api.paymentService.PaymentServiceApi
import com.ninjasquad.springmockk.MockkBean
import io.cucumber.java.Before
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import io.cucumber.spring.CucumberContextConfiguration
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
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

@Testcontainers
@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class RegisterVehicleAcceptanceTest {
    private val vehiclesUrl = "/v1/vehicles"

    @Autowired private lateinit var testRestTemplate: TestRestTemplate
    @MockkBean lateinit var vehicleSalesServiceApi: VehicleSalesServiceApi
    @MockkBean lateinit var paymentServiceApi: PaymentServiceApi

    private lateinit var response: ResponseEntity<String>
    private lateinit var requestInput: Map<String, Any>

    companion object {
        @Container @ServiceConnection val postgres = PostgreSQLContainer("postgres:16.3")
    }

    @Before
    fun mockApiCalls() {
        coEvery { vehicleSalesServiceApi.saveVehicle(any()) } coAnswers
            {
                SaveVehicleResponse(result = "Vehicle registration sent")
            }

        coEvery { vehicleSalesServiceApi.updateVehicle(any()) } coAnswers
            {
                UpdateVehicleResponse(result = "Vehicle update sent")
            }

        coEvery { vehicleSalesServiceApi.notifyPayment(any()) } coAnswers
            {
                NotifyPaymentResponse(result = "Payment notification sent")
            }

        coEvery { paymentServiceApi.generateQRCode(any()) } coAnswers
            {
                GenerateQRCodeResponse("mocked_qr_code")
            }
    }

    @Before
    fun `should connection be established`() {
        postgres.isCreated shouldBe true
        postgres.isRunning shouldBe true
    }

    @Given("the system has a valid vehicle registration form")
    fun `the system has a valid vehicle registration form`() {
        requestInput =
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
    }

    @When("the admin submits the vehicle registration form")
    fun `the admin submits the vehicle registration form`() {
        response = testRestTemplate.postForEntity(vehiclesUrl, requestInput, String::class.java)
    }

    @Then("the vehicle should be registered successfully")
    fun `the vehicle should be registered successfully`() {
        response.statusCode shouldBe HttpStatus.CREATED
        response.statusCode.is2xxSuccessful shouldBe true
    }

    @Given("the system has an invalid vehicle registration form missing required fields")
    fun `the system has an invalid vehicle registration form missing required fields`() {
        requestInput =
            mapOf(
                "make" to "Fiat",
                "model" to "Uno",
                // Missing required fields like plate, price, etc.
            )
    }

    @Then("the system should reject the registration with a bad request error")
    fun `the system should reject the registration with a bad request error`() {
        response.statusCode shouldBe HttpStatus.BAD_REQUEST
    }
}
