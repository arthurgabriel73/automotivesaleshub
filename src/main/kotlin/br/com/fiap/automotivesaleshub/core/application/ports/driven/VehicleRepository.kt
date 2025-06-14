package br.com.fiap.automotivesaleshub.core.application.ports.driven

import br.com.fiap.automotivesaleshub.core.domain.vehicle.models.Vehicle
import br.com.fiap.automotivesaleshub.core.domain.vehicle.valueObjects.Plate
import br.com.fiap.automotivesaleshub.core.domain.vehicle.valueObjects.VehicleId

interface VehicleRepository {
    fun create(vehicle: Vehicle): Vehicle

    fun findById(vehicleId: VehicleId): Vehicle?

    fun findByPlate(plate: Plate): Vehicle?

    fun update(vehicle: Vehicle): Vehicle
}
