package br.com.fiap.automotivesaleshub.adapters.driven.persistence

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface JpaVehicleRepository : JpaRepository<VehicleEntity, UUID> {
    fun findByVehicleId(vehicleId: UUID): VehicleEntity?

    fun findByPlate(plate: String): VehicleEntity?
}
