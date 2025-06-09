package br.com.fiap.automotivesaleshub.core.domain.vehicle.models

import br.com.fiap.automotivesaleshub.core.domain.vehicle.exceptions.InvalidVehicleStatusException
import br.com.fiap.automotivesaleshub.core.domain.vehicle.valueObjects.*
import java.time.Instant

class Vehicle(
    val vehicleId: VehicleId,
    specifications: Specifications,
    plate: Plate,
    price: Price,
    status: VehicleStatus,
    createdAt: Instant,
    updatedAt: Instant? = null,
) {

    var specifications = specifications
        private set

    var plate = plate
        private set

    var price = price
        private set

    var status = status
        private set

    var createdAt = createdAt
        private set

    var updatedAt = updatedAt
        private set

    fun updateSpecifications(newSpecifications: Specifications): Vehicle {
        return updateField { specifications = newSpecifications }
    }

    fun updatePlate(newPlate: Plate): Vehicle {
        return updateField { plate = newPlate }
    }

    fun updatePrice(newPrice: Price): Vehicle {
        return updateField { price = newPrice }
    }

    @Throws(InvalidVehicleStatusException::class)
    fun updateStatus(newStatus: VehicleStatus): Vehicle {
        if (!isValidStatusTransition(status, newStatus))
            throw InvalidVehicleStatusException("Invalid status update from $status to $newStatus")
        return updateField { status = newStatus }
    }

    private fun isValidStatusTransition(current: VehicleStatus, new: VehicleStatus): Boolean {
        val validTransitions =
            mapOf(
                VehicleStatus.AVAILABLE to
                    setOf(VehicleStatus.PROCESSING, VehicleStatus.UNAVAILABLE),
                VehicleStatus.PROCESSING to setOf(VehicleStatus.SOLD, VehicleStatus.UNAVAILABLE),
                VehicleStatus.UNAVAILABLE to setOf(VehicleStatus.AVAILABLE),
                VehicleStatus.SOLD to emptySet(),
            )
        return new in (validTransitions[current] ?: emptySet())
    }

    private fun updateField(updateAction: () -> Unit): Vehicle {
        updateAction()
        updatedAt = Instant.now()
        return this
    }
}
