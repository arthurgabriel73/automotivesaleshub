package br.com.fiap.automotivesaleshub.adapters.driven.api.dto.request

import br.com.fiap.automotivesaleshub.core.domain.vehicle.models.Vehicle

data class SaveVehicleRequest(private val vehicle: Vehicle) {
    fun toMap(): Map<String, Any> {
        return mapOf(
            "vehicleId" to vehicle.vehicleId.string(),
            "plate" to vehicle.plate.plate,
            "price" to vehicle.price.amount,
            "priceCurrency" to vehicle.price.currency,
            "make" to vehicle.specifications.make,
            "model" to vehicle.specifications.model,
            "version" to vehicle.specifications.version,
            "yearFabrication" to vehicle.specifications.yearFabrication,
            "yearModel" to vehicle.specifications.yearModel,
            "kilometers" to vehicle.specifications.kilometers,
            "color" to vehicle.specifications.color,
            "status" to vehicle.status.name,
            "createdAt" to vehicle.createdAt.toString(),
            "updatedAt" to vehicle.updatedAt.toString(),
        )
    }
}
