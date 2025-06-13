package br.com.fiap.automotivesaleshub.adapters.driven.persistence

import br.com.fiap.automotivesaleshub.core.application.ports.driven.VehicleRepository
import br.com.fiap.automotivesaleshub.core.domain.vehicle.models.Vehicle
import jakarta.inject.Named

@Named
class VehicleRepositoryAdapter(private val jpaRepository: JpaVehicleRepository) :
    VehicleRepository {

    override fun create(vehicle: Vehicle): Vehicle {
        val entity = VehicleEntity.fromDomain(vehicle)
        return jpaRepository.save(entity).toDomain()
    }

    override fun findByPlate(plate: String): Vehicle? {
        return jpaRepository.findByPlate(plate)?.toDomain()
    }
}
