package br.com.fiap.automotivesaleshub.acceptance

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.transaction.annotation.Transactional
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class RegisterVehicleAcceptanceTest {
    private val vehiclesUrl = "/v1/vehicles"

    @Autowired private lateinit var testRestTemplate: TestRestTemplate

    companion object {
        @Container @ServiceConnection val postgres = PostgreSQLContainer("postgres:16.3")
    }

    @Test
    fun `should connection be established`() {
        postgres.isCreated shouldBe true
        postgres.isRunning shouldBe true
    }

    @Test
    fun `should register vehicle successfully`() {
        // Arrange
        val requestInput =
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

        // Act
        val response = testRestTemplate.postForEntity(vehiclesUrl, requestInput, String::class.java)

        // Assert
        response.statusCode.is2xxSuccessful shouldBe true
    }
}
