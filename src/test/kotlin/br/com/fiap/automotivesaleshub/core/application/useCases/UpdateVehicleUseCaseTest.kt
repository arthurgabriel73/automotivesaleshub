package br.com.fiap.automotivesaleshub.core.application.useCases

import br.com.fiap.automotivesaleshub.adapters.driven.api.InMemoryVehicleSalesService
import br.com.fiap.automotivesaleshub.adapters.driven.persistence.InMemoryVehicleRepository
import br.com.fiap.automotivesaleshub.core.application.ports.driver.models.input.UpdateVehicleInput
import br.com.fiap.automotivesaleshub.core.application.useCases.exceptions.VehicleIsAlreadyRegisteredException
import br.com.fiap.automotivesaleshub.core.application.useCases.exceptions.VehicleNotFoundException
import br.com.fiap.automotivesaleshub.core.domain.vehicle.models.Vehicle
import br.com.fiap.automotivesaleshub.core.domain.vehicle.valueObjects.*
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import java.time.Instant
import java.util.*
import kotlin.test.Test

class UpdateVehicleUseCaseTest {
    private lateinit var vehicleRepository: InMemoryVehicleRepository
    private lateinit var vehicleSalesService: InMemoryVehicleSalesService
    private lateinit var sut: UpdateVehicleUseCase

    @BeforeEach
    fun setUp() {
        vehicleRepository = InMemoryVehicleRepository()
        vehicleSalesService = InMemoryVehicleSalesService()
        sut = UpdateVehicleUseCase(vehicleRepository, vehicleSalesService)
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

    val updateVehicleInput: UpdateVehicleInput =
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
        val output = sut.execute(updateVehicleInput)

        // Assert
        assert(output.vehicleId.isNotEmpty())
        val updatedVehicle =
            vehicleRepository.findById(VehicleId(UUID.fromString(updateVehicleInput.vehicleId)))
        if (updatedVehicle == null) throw AssertionError("Updated vehicle should not be null")
        updatedVehicle shouldNotBe null
        updatedVehicle.plate.plate shouldBe updateVehicleInput.plate
        updatedVehicle.specifications.make shouldBe updateVehicleInput.make
        updatedVehicle.specifications.model shouldBe updateVehicleInput.model
        updatedVehicle.specifications.version shouldBe updateVehicleInput.version
        updatedVehicle.specifications.yearFabrication shouldBe updateVehicleInput.yearFabrication
        updatedVehicle.specifications.yearModel shouldBe updateVehicleInput.yearModel
        updatedVehicle.specifications.kilometers shouldBe updateVehicleInput.kilometers
        updatedVehicle.specifications.color shouldBe updateVehicleInput.color
        updatedVehicle.price.amount shouldBe updateVehicleInput.price
        updatedVehicle.price.currency shouldBe updateVehicleInput.priceCurrency
        updatedVehicle.status shouldBe updateVehicleInput.status
        updatedVehicle.createdAt shouldBe existingVehicle.createdAt
        updatedVehicle.updatedAt shouldNotBe null
        updatedVehicle.updatedAt!!.isAfter(existingVehicle.createdAt) shouldBe true
    }

    @Test
    fun `should throw VehicleNotFoundException when vehicle does not exist`() {
        // Act & Assert
        shouldThrow<VehicleNotFoundException> { sut.execute(updateVehicleInput) }
    }

    @Test
    fun `should throw VehicleIsAlreadyRegisteredException when updating with existing plate`() {
        // Arrange
        val anotherVehicle =
            Vehicle(
                vehicleId = VehicleId(UUID.randomUUID()),
                specifications =
                    Specifications(
                        make = "Toyota",
                        model = "Corolla",
                        version = "XEI",
                        yearFabrication = "2020",
                        yearModel = "2021",
                        kilometers = 10000,
                        color = "Black",
                    ),
                plate = Plate(plate = "ABC-1B34"), // Same plate as input
                price = Price(amount = 40000L, currency = PriceCurrency.BRL),
                status = VehicleStatus.AVAILABLE,
                createdAt = Instant.now(),
            )
        vehicleRepository.create(existingVehicle)
        vehicleRepository.create(anotherVehicle)

        // Act & Assert
        shouldThrow<VehicleIsAlreadyRegisteredException> { sut.execute(updateVehicleInput) }
    }

    @Test
    fun `should send vehicle changes to sales service`(): Unit = runBlocking {
        // Arrange
        vehicleRepository.create(existingVehicle)
        vehicleSalesService.saveVehicle(existingVehicle)

        // Act
        val output = sut.execute(updateVehicleInput)
        delay(100) // Wait for async operation to complete

        // Assert
        val vehicleFromSalesService =
            vehicleSalesService.vehicles.find { it.vehicleId == existingVehicleId }
        if (vehicleFromSalesService == null)
            throw AssertionError("Vehicle should not be null in sales service")
        output.vehicleId shouldNotBe null
        vehicleFromSalesService.plate.plate shouldBe updateVehicleInput.plate
    }
}
