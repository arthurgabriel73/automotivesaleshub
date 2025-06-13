package br.com.fiap.automotivesaleshub.adapters.driven.api

import br.com.fiap.automotivesaleshub.core.application.ports.driven.VehicleSalesService
import br.com.fiap.automotivesaleshub.core.domain.vehicle.models.Vehicle

class InMemoryVehicleSalesService : VehicleSalesService {
    val vehicles = mutableListOf<Vehicle>()

    override suspend fun saveVehicle(vehicle: Vehicle) {
        vehicles.add(vehicle)
    }
}
