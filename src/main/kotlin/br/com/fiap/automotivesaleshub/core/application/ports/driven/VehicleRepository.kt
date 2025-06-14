package br.com.fiap.automotivesaleshub.core.application.ports.driven

import br.com.fiap.automotivesaleshub.core.domain.vehicle.models.Vehicle

interface VehicleRepository {
    fun create(vehicle: Vehicle): Vehicle

    fun findByPlate(plate: String): Vehicle?

    fun update(vehicle: Vehicle): Vehicle
}
