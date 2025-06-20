package br.com.fiap.automotivesaleshub.adapters.driven.api.dto.request

import br.com.fiap.automotivesaleshub.core.domain.vehicle.models.Vehicle

data class GenerateQRCodeRequest(private val orderId: String, private val vehicle: Vehicle) {
    fun toMap(): Map<String, Any> {
        return mapOf("orderId" to orderId)
    }
}
