package br.com.fiap.automotivesaleshub.adapters.driven.persistence

import br.com.fiap.automotivesaleshub.core.application.ports.driven.VehicleRepository
import br.com.fiap.automotivesaleshub.core.domain.vehicle.models.Vehicle
import br.com.fiap.automotivesaleshub.core.domain.vehicle.valueObjects.Plate
import br.com.fiap.automotivesaleshub.core.domain.vehicle.valueObjects.VehicleId

class InMemoryVehicleRepository : VehicleRepository {
    val vehicles = mutableListOf<Vehicle>()

    override fun create(vehicle: Vehicle): Vehicle {
        vehicles.add(vehicle)
        return toNewInstance(vehicle)
    }

    override fun findById(vehicleId: VehicleId): Vehicle? {
        return vehicles.find { it.vehicleId.id == vehicleId.id }?.let { toNewInstance(it) }
    }

    override fun findByPlate(plate: Plate): Vehicle? {
        return toNewInstance(vehicles.find { it.plate == plate } ?: return null)
    }

    override fun update(vehicle: Vehicle): Vehicle {
        val index = vehicles.indexOfFirst { it.vehicleId == vehicle.vehicleId }
        if (index == -1)
            throw IllegalArgumentException("Vehicle with ID ${vehicle.vehicleId} not found")
        vehicles[index] = toNewInstance(vehicle)
        return toNewInstance(vehicles[index])
    }

    private fun toNewInstance(vehicle: Vehicle): Vehicle {
        return Vehicle(
            vehicleId = vehicle.vehicleId,
            specifications = vehicle.specifications,
            plate = vehicle.plate,
            price = vehicle.price,
            status = vehicle.status,
            createdAt = vehicle.createdAt,
            updatedAt = vehicle.updatedAt,
        )
    }
}
