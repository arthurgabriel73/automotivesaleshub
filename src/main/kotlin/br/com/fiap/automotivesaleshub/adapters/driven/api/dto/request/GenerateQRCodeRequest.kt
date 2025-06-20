package br.com.fiap.automotivesaleshub.adapters.driven.api.dto.request

import br.com.fiap.automotivesaleshub.core.domain.vehicle.models.Vehicle

data class GenerateQRCodeRequest(val orderId: String, val vehicle: Vehicle)
