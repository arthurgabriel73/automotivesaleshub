package br.com.fiap.automotivesaleshub.adapters.driver.rest.dto

import br.com.fiap.automotivesaleshub.core.application.ports.driver.models.input.PurchaseVehicleInput
import jakarta.validation.constraints.NotBlank
import org.hibernate.validator.constraints.UUID

data class PurchaseVehicleRequest(
    @field:NotBlank
    @field:UUID(message = "Order ID is required and must be a valid UUID")
    val orderId: String,
    @field:NotBlank
    @field:UUID(message = "Vehicle ID is required and must be a valid UUID")
    val vehicleId: String,
) {
    fun toInput() = PurchaseVehicleInput(orderId = orderId, vehicleId = vehicleId)
}
