package br.com.fiap.automotivesaleshub.adapters.driven.api.automotivesalesservice

import br.com.fiap.automotivesaleshub.adapters.driven.api.VehicleSalesServiceApi
import br.com.fiap.automotivesaleshub.adapters.driven.api.dto.request.SaveVehicleRequest
import br.com.fiap.automotivesaleshub.adapters.driven.api.dto.request.UpdateVehicleRequest
import br.com.fiap.automotivesaleshub.adapters.driven.api.dto.response.SaveVehicleResponse
import br.com.fiap.automotivesaleshub.adapters.driven.api.dto.response.UpdateVehicleResponse
import jakarta.inject.Named
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.web.client.RestTemplate

@Named
class AutomotiveSalesServiceApi() : VehicleSalesServiceApi {

    private var restTemplate: RestTemplate = RestTemplate()

    @Value("\${automotive.sales.service.url}") lateinit var baseUrl: String

    override suspend fun saveVehicle(request: SaveVehicleRequest): SaveVehicleResponse {
        restTemplate.postForEntity(
            "$baseUrl/vehicles",
            HttpEntity(request.toMap()),
            String::class.java,
        )
        return SaveVehicleResponse(result = "Vehicle saved successfully")
    }

    override suspend fun updateVehicle(request: UpdateVehicleRequest): UpdateVehicleResponse {
        restTemplate.exchange(
            "$baseUrl/vehicles",
            HttpMethod.PUT,
            HttpEntity(request.toMap()),
            String::class.java,
        )
        return UpdateVehicleResponse(result = "Vehicle updated successfully")
    }
}
