package br.com.fiap.automotivesaleshub.core.application.useCases

import br.com.fiap.automotivesaleshub.adapters.driven.persistence.InMemoryVehicleRepository
import br.com.fiap.automotivesaleshub.core.application.ports.driver.models.input.UpdateVehicleInput
import br.com.fiap.automotivesaleshub.core.application.useCases.exceptions.VehicleNotFoundException
import br.com.fiap.automotivesaleshub.core.domain.vehicle.models.Vehicle
import br.com.fiap.automotivesaleshub.core.domain.vehicle.valueObjects.*
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.BeforeEach
import java.time.Instant
import java.util.*
import kotlin.test.Test

class UpdateVehicleUseCaseTest {
    private lateinit var vehicleRepository: InMemoryVehicleRepository
    private lateinit var sut: UpdateVehicleUseCase

    @BeforeEach
    fun setUp() {
        vehicleRepository = InMemoryVehicleRepository()
        sut = UpdateVehicleUseCase(vehicleRepository)
    }

    val existingVehicleId = VehicleId(UUID.randomUUID())
    val existingVehicle =
        Vehicle(
            vehicleId = existingVehicleId,
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
            plate = Plate(plate = "ABC-1234"),
            price = Price(amount = 30000L, currency = PriceCurrency.BRL),
            status = VehicleStatus.AVAILABLE,
            createdAt = Instant.now(),
        )

    val input: UpdateVehicleInput =
        UpdateVehicleInput(
            vehicleId = existingVehicleId.string(),
            make = "Fiat Updated",
            model = "Uno Updated",
            version = "Way Updated",
            yearFabrication = "2020 Updated",
            yearModel = "2021 Updated",
            kilometers = 15200,
            color = "Blue",
            plate = "ABC-1B34",
            price = 5000L,
            priceCurrency = PriceCurrency.USD,
            status = VehicleStatus.PROCESSING,
        )

    @Test
    fun `should update a vehicle successfully`() {
        // Arrange
        vehicleRepository.create(existingVehicle)

        // Act
        val output = sut.execute(input)

        // Assert
        assert(output.vehicleId.isNotEmpty())
        val updatedVehicle = vehicleRepository.findById(VehicleId(UUID.fromString(input.vehicleId)))
        if (updatedVehicle == null) throw AssertionError("Updated vehicle should not be null")
        updatedVehicle shouldNotBe null
        updatedVehicle.plate.plate shouldBe input.plate
        updatedVehicle.specifications.make shouldBe input.make
        updatedVehicle.specifications.model shouldBe input.model
        updatedVehicle.specifications.version shouldBe input.version
        updatedVehicle.specifications.yearFabrication shouldBe input.yearFabrication
        updatedVehicle.specifications.yearModel shouldBe input.yearModel
        updatedVehicle.specifications.kilometers shouldBe input.kilometers
        updatedVehicle.specifications.color shouldBe input.color
        updatedVehicle.price.amount shouldBe input.price
        updatedVehicle.price.currency shouldBe input.priceCurrency
        updatedVehicle.status shouldBe input.status
        updatedVehicle.createdAt shouldBe existingVehicle.createdAt
        updatedVehicle.updatedAt shouldNotBe null
        updatedVehicle.updatedAt!!.isAfter(existingVehicle.createdAt) shouldBe true
    }

    @Test
    fun `should throw VehicleNotFoundException when vehicle does not exist`() {
        // Act & Assert
        shouldThrow<VehicleNotFoundException> { sut.execute(input) }
    }

    @Test
    fun `should throw VehicleIsAlreadyRegisteredException when updating with existing plate`() {
        TODO()
    }
}
