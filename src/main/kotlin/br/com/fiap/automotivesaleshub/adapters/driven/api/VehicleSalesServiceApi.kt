package br.com.fiap.automotivesaleshub.adapters.driven.api

import br.com.fiap.automotivesaleshub.adapters.driven.api.dto.request.SaveVehicleRequest
import br.com.fiap.automotivesaleshub.adapters.driven.api.dto.response.SaveVehicleResponse

interface VehicleSalesServiceApi {
    suspend fun saveVehicle(request: SaveVehicleRequest): SaveVehicleResponse
}
