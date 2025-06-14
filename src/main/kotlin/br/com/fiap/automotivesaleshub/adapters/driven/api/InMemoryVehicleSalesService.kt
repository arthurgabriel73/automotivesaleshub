package br.com.fiap.automotivesaleshub.adapters.driven.api

import br.com.fiap.automotivesaleshub.core.application.ports.driven.VehicleSalesService
import br.com.fiap.automotivesaleshub.core.domain.vehicle.models.Vehicle

class InMemoryVehicleSalesService : VehicleSalesService {
    val vehicles = mutableListOf<Vehicle>()

    override suspend fun saveVehicle(vehicle: Vehicle) {
        vehicles.add(toNewInstance(vehicle))
    }

    override suspend fun updateVehicle(vehicle: Vehicle) {
        val index = vehicles.indexOfFirst { it.vehicleId == vehicle.vehicleId }
        if (index == -1) return
        vehicles[index] = toNewInstance(vehicle)
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
