package br.com.fiap.automotivesaleshub.adapters.driven.api.automotivesalesservice

import br.com.fiap.automotivesaleshub.adapters.driven.api.VehicleSalesServiceApi
import br.com.fiap.automotivesaleshub.adapters.driven.api.dto.request.SaveVehicleRequest
import br.com.fiap.automotivesaleshub.adapters.driven.api.dto.request.UpdateVehicleRequest
import br.com.fiap.automotivesaleshub.adapters.driven.api.dto.response.SaveVehicleResponse
import br.com.fiap.automotivesaleshub.adapters.driven.api.dto.response.UpdateVehicleResponse
import jakarta.inject.Named
import org.springframework.web.reactive.function.client.WebClient

@Named
class AutomotiveSalesServiceApi(private val webClient: WebClient) : VehicleSalesServiceApi {
    private val baseUrl = "http://automotive-sales-service"

    override suspend fun saveVehicle(request: SaveVehicleRequest): SaveVehicleResponse {
        //        webClient
        //            .post()
        //            .uri("https://webhook.site/95dcf8ed-c274-457f-93bd-0c4aab465fe8")
        //            .bodyValue(request.toString())
        //            .retrieve()
        //            .bodyToMono(String::class.java)
        //            .block() ?: throw RuntimeException("Failed to save vehicle")
        return SaveVehicleResponse(result = "Vehicle saved successfully")
    }

    override suspend fun updateVehicle(request: UpdateVehicleRequest): UpdateVehicleResponse {
        //        webClient
        //            .put()
        //            .uri("https://webhook.site/95dcf8ed-c274-457f-93bd-0c4aab465fe8")
        //            .bodyValue(request.toString())
        //            .retrieve()
        //            .bodyToMono(String::class.java)
        //            .block() ?: throw RuntimeException("Failed to update vehicle")
        return UpdateVehicleResponse(result = "Vehicle updated successfully")
    }
}
