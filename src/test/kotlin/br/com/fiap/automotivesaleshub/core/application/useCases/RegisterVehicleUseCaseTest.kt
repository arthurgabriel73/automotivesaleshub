package br.com.fiap.automotivesaleshub.core.application.useCases

import br.com.fiap.automotivesaleshub.adapters.driven.api.InMemoryVehicleSalesService
import br.com.fiap.automotivesaleshub.adapters.driven.persistence.InMemoryVehicleRepository
import br.com.fiap.automotivesaleshub.core.application.ports.driver.models.input.RegisterVehicleInput
import br.com.fiap.automotivesaleshub.core.application.useCases.exceptions.VehicleIsAlreadyRegisteredException
import br.com.fiap.automotivesaleshub.core.domain.vehicle.valueObjects.Plate
import br.com.fiap.automotivesaleshub.core.domain.vehicle.valueObjects.PriceCurrency
import br.com.fiap.automotivesaleshub.core.domain.vehicle.valueObjects.VehicleStatus
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class RegisterVehicleUseCaseTest {

    private lateinit var vehicleRepository: InMemoryVehicleRepository
    private lateinit var vehicleSalesService: InMemoryVehicleSalesService
    private lateinit var sut: RegisterVehicleUseCase

    @BeforeEach
    fun setUp() {
        vehicleRepository = InMemoryVehicleRepository()
        vehicleSalesService = InMemoryVehicleSalesService()
        sut = RegisterVehicleUseCase(vehicleRepository, vehicleSalesService)
    }

    val input: RegisterVehicleInput =
        RegisterVehicleInput(
            make = "Fiat",
            model = "Uno",
            version = "Way",
            yearFabrication = "2020",
            yearModel = "2021",
            kilometers = 15000,
            color = "Red",
            plate = "ABC-1234",
            price = 30000L,
            priceCurrency = PriceCurrency.BRL,
            status = VehicleStatus.AVAILABLE,
        )

    @Test
    fun `should register a vehicle successfully`() {

        // Act
        val output = sut.execute(input)

        // Assert
        assertNotNull(output.vehicleId.isNotEmpty())
        val vehicle = vehicleRepository.findByPlate(Plate(input.plate))
        assertNotNull(vehicle)
        assertEquals(input.plate, vehicle.plate.plate)
    }

    @Test
    fun `should throw exception when registering vehicle with existing plate`() {

        // Arrange
        sut.execute(input)

        // Act & Assert
        assertThrows<VehicleIsAlreadyRegisteredException> { sut.execute(input) }
    }

    @Test
    fun `should save vehicle to sales service`(): Unit = runBlocking {
        // Act
        val output = sut.execute(input)
        delay(100) // Wait for async operation to complete

        // Assert
        assertNotNull(output.vehicleId.isNotEmpty())
        assertNotNull(vehicleSalesService.vehicles.find { it.plate.plate == input.plate })
    }
}
