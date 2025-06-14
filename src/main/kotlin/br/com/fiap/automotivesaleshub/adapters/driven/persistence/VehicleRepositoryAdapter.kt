package br.com.fiap.automotivesaleshub.adapters.driven.persistence

import br.com.fiap.automotivesaleshub.core.application.ports.driven.VehicleRepository
import br.com.fiap.automotivesaleshub.core.domain.vehicle.models.Vehicle
import br.com.fiap.automotivesaleshub.core.domain.vehicle.valueObjects.Plate
import br.com.fiap.automotivesaleshub.core.domain.vehicle.valueObjects.VehicleId
import jakarta.inject.Named

@Named
class VehicleRepositoryAdapter(private val jpaRepository: JpaVehicleRepository) :
    VehicleRepository {

    override fun create(vehicle: Vehicle): Vehicle {
        val entity = VehicleEntity.fromDomain(vehicle)
        return jpaRepository.save(entity).toDomain()
    }

    override fun findById(vehicleId: VehicleId): Vehicle? {
        return jpaRepository.findByVehicleId(vehicleId.id)?.toDomain()
    }

    override fun findByPlate(plate: Plate): Vehicle? {
        return jpaRepository.findByPlate(plate.plate)?.toDomain()
    }

    override fun update(vehicle: Vehicle): Vehicle {
        TODO("Not yet implemented")
    }
}
