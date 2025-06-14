package br.com.fiap.automotivesaleshub.adapters.driven.api

import br.com.fiap.automotivesaleshub.adapters.driven.api.dto.request.SaveVehicleRequest
import br.com.fiap.automotivesaleshub.core.application.ports.driven.VehicleSalesService
import br.com.fiap.automotivesaleshub.core.domain.vehicle.models.Vehicle
import jakarta.inject.Named

@Named
class VehicleSalesServiceGateway(private val vehicleSalesServiceApi: VehicleSalesServiceApi) :
    VehicleSalesService {

    override suspend fun saveVehicle(vehicle: Vehicle) {
        vehicleSalesServiceApi.saveVehicle(SaveVehicleRequest(vehicle))
    }

    override suspend fun updateVehicle(vehicle: Vehicle) {
        TODO()
    }
}
