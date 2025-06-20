package br.com.fiap.automotivesaleshub.core.application.ports.driven

import br.com.fiap.automotivesaleshub.core.domain.payment.valueObjects.PaymentStatus
import br.com.fiap.automotivesaleshub.core.domain.vehicle.models.Vehicle

interface VehicleSalesService {
    suspend fun saveVehicle(vehicle: Vehicle)

    suspend fun updateVehicle(vehicle: Vehicle)

    suspend fun notifyPayment(orderId: String, paymentStatus: PaymentStatus)
}
