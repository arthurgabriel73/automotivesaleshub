package br.com.fiap.automotivesaleshub.core.application.useCases

import br.com.fiap.automotivesaleshub.adapters.driven.api.InMemoryVehicleSalesService
import br.com.fiap.automotivesaleshub.adapters.driven.persistence.InMemoryVehicleRepository
import br.com.fiap.automotivesaleshub.core.application.ports.driver.models.input.RegisterVehicleInput
import br.com.fiap.automotivesaleshub.core.application.useCases.exceptions.VehicleIsAlreadyRegisteredException
import br.com.fiap.automotivesaleshub.core.domain.vehicle.valueObjects.PriceCurrency
import br.com.fiap.automotivesaleshub.core.domain.vehicle.valueObjects.VehicleStatus
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class RegisterVehicleUseCaseTest {

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
        // Arrange
        val (sut, vehicleRepository) = makeSut()

        // Act
        val output = sut.execute(input)

        // Assert
        assertNotNull(output.vehicleId.isNotEmpty())
        val vehicle = vehicleRepository.findByPlate(input.plate)
        assertNotNull(vehicle)
        assertEquals(input.plate, vehicle.plate.plate)
    }

    @Test
    fun `should throw exception when registering vehicle with existing plate`() {
        // Arrange
        val (sut) = makeSut()

        // Act
        sut.execute(input)

        // Assert
        assertThrows<VehicleIsAlreadyRegisteredException> { sut.execute(input) }
    }

    @Test
    fun `should save vehicle to sales service`() {
        // Arrange
        val (sut, _, vehicleSalesService) = makeSut()

        // Act
        val output = sut.execute(input)

        // Assert
        assertNotNull(output.vehicleId.isNotEmpty())
        assertNotNull(vehicleSalesService.vehicles.find { it.plate.plate == input.plate })
    }

    data class SutComponents(
        val sut: RegisterVehicleUseCase,
        val vehicleRepository: InMemoryVehicleRepository,
        val vehicleSalesService: InMemoryVehicleSalesService,
    )

    fun makeSut(): SutComponents {
        val vehicleRepository = InMemoryVehicleRepository()
        val vehicleSalesService = InMemoryVehicleSalesService()
        val sut =
            RegisterVehicleUseCase(
                vehicleRepository = vehicleRepository,
                vehicleSalesService = vehicleSalesService,
            )
        return SutComponents(sut, vehicleRepository, vehicleSalesService)
    }
}
