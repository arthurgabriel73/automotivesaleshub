package br.com.fiap.automotivesaleshub.core.application.useCases

import br.com.fiap.automotivesaleshub.adapters.driven.persistence.InMemoryVehicleRepository
import br.com.fiap.automotivesaleshub.core.application.ports.driver.models.input.RegisterVehicleInput
import br.com.fiap.automotivesaleshub.core.application.useCases.exceptions.VehicleIsAlreadyRegisteredException
import br.com.fiap.automotivesaleshub.core.domain.vehicle.valueObjects.PriceCurrency
import br.com.fiap.automotivesaleshub.core.domain.vehicle.valueObjects.VehicleStatus
import kotlin.test.Test
import kotlin.test.assertNotNull
import org.junit.jupiter.api.assertThrows

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
        val sut = makeSut()

        // Act
        val output = sut.execute(input)

        // Assert
        assertNotNull(output.vehicleId.isNotEmpty())
    }

    @Test
    fun `should throw exception when registering vehicle with existing plate`() {
        // Arrange
        val sut = makeSut()

        // Act
        sut.execute(input)

        // Assert
        assertThrows<VehicleIsAlreadyRegisteredException> { sut.execute(input) }
    }

    companion object {
        @JvmStatic
        fun makeSut(): RegisterVehicleUseCase {
            val vehicleRepository = InMemoryVehicleRepository()
            return RegisterVehicleUseCase(vehicleRepository)
        }
    }
}
