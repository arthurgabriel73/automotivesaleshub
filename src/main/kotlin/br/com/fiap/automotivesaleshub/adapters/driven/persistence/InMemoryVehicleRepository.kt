package br.com.fiap.automotivesaleshub.adapters.driven.persistence

import br.com.fiap.automotivesaleshub.core.application.ports.driven.VehicleRepository
import br.com.fiap.automotivesaleshub.core.domain.vehicle.models.Vehicle

class InMemoryVehicleRepository : VehicleRepository {
    private val vehicles = mutableListOf<Vehicle>()

    override fun create(vehicle: Vehicle): Vehicle {
        vehicles.add(vehicle)
        return toNewInstance(vehicle)
    }

    override fun findByPlate(plate: String): Vehicle? {
        return toNewInstance(vehicles.find { it.plate.plate == plate } ?: return null)
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
