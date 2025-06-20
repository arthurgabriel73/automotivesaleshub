package br.com.fiap.automotivesaleshub.adapters.driven.api

import br.com.fiap.automotivesaleshub.adapters.driven.api.dto.request.NotifyPaymentRequest
import br.com.fiap.automotivesaleshub.adapters.driven.api.dto.request.SaveVehicleRequest
import br.com.fiap.automotivesaleshub.adapters.driven.api.dto.request.UpdateVehicleRequest
import br.com.fiap.automotivesaleshub.adapters.driven.api.dto.response.SaveVehicleResponse
import br.com.fiap.automotivesaleshub.adapters.driven.api.dto.response.UpdateVehicleResponse

interface VehicleSalesServiceApi {
    suspend fun saveVehicle(request: SaveVehicleRequest): SaveVehicleResponse

    suspend fun updateVehicle(request: UpdateVehicleRequest): UpdateVehicleResponse

    suspend fun notifyPayment(request: NotifyPaymentRequest)
}
