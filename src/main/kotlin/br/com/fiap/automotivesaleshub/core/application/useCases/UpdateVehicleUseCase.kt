package br.com.fiap.automotivesaleshub.core.application.useCases

import br.com.fiap.automotivesaleshub.core.application.ports.driven.VehicleRepository
import br.com.fiap.automotivesaleshub.core.application.ports.driver.models.UpdateVehicleDriverPort
import br.com.fiap.automotivesaleshub.core.application.ports.driver.models.input.UpdateVehicleInput
import br.com.fiap.automotivesaleshub.core.application.ports.driver.models.output.UpdateVehicleOutput
import br.com.fiap.automotivesaleshub.core.application.useCases.exceptions.VehicleIsAlreadyRegisteredException
import br.com.fiap.automotivesaleshub.core.application.useCases.exceptions.VehicleNotFoundException
import br.com.fiap.automotivesaleshub.core.domain.vehicle.models.Vehicle
import br.com.fiap.automotivesaleshub.core.domain.vehicle.valueObjects.Plate
import br.com.fiap.automotivesaleshub.core.domain.vehicle.valueObjects.Price
import br.com.fiap.automotivesaleshub.core.domain.vehicle.valueObjects.Specifications
import br.com.fiap.automotivesaleshub.core.domain.vehicle.valueObjects.VehicleId
import java.time.Instant
import java.util.*

class UpdateVehicleUseCase(val vehicleRepository: VehicleRepository) : UpdateVehicleDriverPort {

    override fun execute(input: UpdateVehicleInput): UpdateVehicleOutput {
        val existingVehicle = getVehicleOrFail(input.vehicleId)
        requirePlateIsAvailable(input.vehicleId, input.plate)
        val vehicleToUpdate =
            Vehicle(
                vehicleId = existingVehicle.vehicleId,
                specifications =
                    Specifications(
                        make = input.make,
                        model = input.model,
                        version = input.version,
                        yearFabrication = input.yearFabrication,
                        yearModel = input.yearModel,
                        kilometers = input.kilometers,
                        color = input.color,
                    ),
                plate = Plate(input.plate),
                price = Price(input.price, input.priceCurrency),
                status = input.status,
                createdAt = existingVehicle.createdAt,
                updatedAt = Instant.now(),
            )
        val updatedVehicle = vehicleRepository.update(vehicleToUpdate)
        return UpdateVehicleOutput(updatedVehicle.vehicleId.toString())
    }

    private fun getVehicleOrFail(id: String): Vehicle {
        val vehicle = vehicleRepository.findById(VehicleId(UUID.fromString(id)))
        if (vehicle == null) throw VehicleNotFoundException("Vehicle with ID $id does not exist.")
        return vehicle
    }

    private fun requirePlateIsAvailable(vehicleId: String, plate: String?) {
        if (plate.isNullOrBlank()) return
        val existingVehicle = vehicleRepository.findByPlate(Plate(plate))
        if (
            existingVehicle != null &&
                existingVehicle.vehicleId.id != VehicleId(UUID.fromString(vehicleId)).id
        )
            throw VehicleIsAlreadyRegisteredException(
                "Update Failed: Another vehicle with plate $plate is already registered."
            )
    }
}
