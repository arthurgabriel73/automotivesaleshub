package br.com.fiap.automotivesaleshub.adapters.driven.api

import br.com.fiap.automotivesaleshub.adapters.driven.api.dto.request.SaveVehicleRequest
import br.com.fiap.automotivesaleshub.adapters.driven.api.dto.request.UpdateVehicleRequest
import br.com.fiap.automotivesaleshub.core.application.ports.driven.VehicleSalesService
import br.com.fiap.automotivesaleshub.core.domain.payment.valueObjects.PaymentStatus
import br.com.fiap.automotivesaleshub.core.domain.vehicle.models.Vehicle
import jakarta.inject.Named

@Named
class VehicleSalesServiceGateway(private val vehicleSalesServiceApi: VehicleSalesServiceApi) :
    VehicleSalesService {

    override suspend fun saveVehicle(vehicle: Vehicle) {
        vehicleSalesServiceApi.saveVehicle(SaveVehicleRequest(vehicle))
    }

    override suspend fun updateVehicle(vehicle: Vehicle) {
        vehicleSalesServiceApi.updateVehicle(UpdateVehicleRequest(vehicle))
    }

    override suspend fun notifyPayment(orderId: String, paymentStatus: PaymentStatus) {}
}
