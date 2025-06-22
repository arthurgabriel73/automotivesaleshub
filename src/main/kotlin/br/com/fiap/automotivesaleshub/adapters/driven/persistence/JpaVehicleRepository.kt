package br.com.fiap.automotivesaleshub.adapters.driven.persistence

import java.util.*
import org.springframework.data.jpa.repository.JpaRepository

interface JpaVehicleRepository : JpaRepository<VehicleEntity, UUID> {
    fun findByVehicleId(vehicleId: UUID): VehicleEntity?

    fun findByPlate(plate: String): VehicleEntity?
}
