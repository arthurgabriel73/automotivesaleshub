package br.com.fiap.automotivesaleshub.core.application.useCases

import br.com.fiap.automotivesaleshub.core.application.ports.driven.VehicleRepository
import br.com.fiap.automotivesaleshub.core.application.ports.driver.models.RegisterVehicleDriverPort
import br.com.fiap.automotivesaleshub.core.application.ports.driver.models.input.RegisterVehicleInput
import br.com.fiap.automotivesaleshub.core.application.ports.driver.models.output.RegisterVehicleOutput
import br.com.fiap.automotivesaleshub.core.application.useCases.exceptions.VehicleIsAlreadyRegisteredException
import br.com.fiap.automotivesaleshub.core.domain.vehicle.models.Vehicle
import br.com.fiap.automotivesaleshub.core.domain.vehicle.valueObjects.*
import java.time.Instant
import java.util.*

class RegisterVehicleUseCase(val vehicleRepository: VehicleRepository) : RegisterVehicleDriverPort {
    override fun execute(input: RegisterVehicleInput): RegisterVehicleOutput {
        requitePlateIsAvailable(input.plate)
        val vehicleId = VehicleId(UUID.randomUUID())
        val specifications =
            Specifications(
                make = input.make,
                model = input.model,
                version = input.version,
                yearFabrication = input.yearFabrication,
                yearModel = input.yearModel,
                kilometers = input.kilometers,
                color = input.color,
            )
        val plate = Plate(input.plate)
        val price = Price(input.price, input.priceCurrency)
        val status = VehicleStatus.AVAILABLE
        val createdAt = Instant.now()
        val vehicle =
            Vehicle(
                vehicleId = vehicleId,
                specifications = specifications,
                plate = plate,
                price = price,
                status = status,
                createdAt = createdAt,
            )
        val createdVehicle = vehicleRepository.create(vehicle)
        return RegisterVehicleOutput(createdVehicle.vehicleId.toString())
    }

    private fun requitePlateIsAvailable(plate: String) {
        val existingVehicle = vehicleRepository.findByPlate(plate)
        if (existingVehicle != null)
            throw VehicleIsAlreadyRegisteredException("Vehicle with plate $plate already exists.")
    }
}
