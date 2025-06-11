package br.com.fiap.automotivesaleshub.adapters.driven.persistence

import br.com.fiap.automotivesaleshub.core.domain.vehicle.models.Vehicle
import br.com.fiap.automotivesaleshub.core.domain.vehicle.valueObjects.*
import jakarta.persistence.*
import java.time.Instant
import java.util.*

@Entity
@Table(name = "vehicles")
data class VehicleEntity(
    @Id val vehicleId: UUID,
    val make: String,
    val model: String,
    val version: String,
    val yearFabrication: String,
    val yearModel: String,
    val kilometers: Int,
    val color: String,
    val plate: String,
    val price: Long,
    @Enumerated(EnumType.STRING)
    val currency: PriceCurrency,
    @Enumerated(EnumType.STRING)
    val status: VehicleStatus,
    val createdAt: Instant,
    val updatedAt: Instant?,
) {
    fun toDomain() =
        Vehicle(
            vehicleId = VehicleId(vehicleId),
            specifications =
                Specifications(
                    make = make,
                    model = model,
                    version = version,
                    yearFabrication = yearFabrication,
                    yearModel = yearModel,
                    kilometers = kilometers,
                    color = color,
                ),
            plate = Plate(plate),
            price = Price(price, currency),
            status = status,
            createdAt = createdAt,
            updatedAt = updatedAt,
        )

    companion object {
        fun fromDomain(vehicle: Vehicle): VehicleEntity {
            return VehicleEntity(
                vehicleId = vehicle.vehicleId.id,
                make = vehicle.specifications.make,
                model = vehicle.specifications.model,
                version = vehicle.specifications.version,
                yearFabrication = vehicle.specifications.yearFabrication,
                yearModel = vehicle.specifications.yearModel,
                kilometers = vehicle.specifications.kilometers,
                color = vehicle.specifications.color,
                plate = vehicle.plate.plate,
                price = vehicle.price.amount,
                currency = vehicle.price.currency,
                status = vehicle.status,
                createdAt = vehicle.createdAt,
                updatedAt = vehicle.updatedAt,
            )
        }
    }
}
