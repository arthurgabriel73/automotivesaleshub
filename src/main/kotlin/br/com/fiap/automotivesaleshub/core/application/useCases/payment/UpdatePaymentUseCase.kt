package br.com.fiap.automotivesaleshub.core.application.useCases.payment

import br.com.fiap.automotivesaleshub.core.application.ports.driven.PaymentRepository
import br.com.fiap.automotivesaleshub.core.application.ports.driven.VehicleRepository
import br.com.fiap.automotivesaleshub.core.application.ports.driven.VehicleSalesService
import br.com.fiap.automotivesaleshub.core.application.ports.payment.driver.UpdatePaymentDriverPort
import br.com.fiap.automotivesaleshub.core.application.ports.payment.driver.models.input.UpdatePaymentInput
import br.com.fiap.automotivesaleshub.core.application.useCases.exceptions.PaymentNotFoundException
import br.com.fiap.automotivesaleshub.core.application.useCases.exceptions.VehicleNotFoundException
import br.com.fiap.automotivesaleshub.core.domain.payment.models.Payment
import br.com.fiap.automotivesaleshub.core.domain.payment.valueObjects.OrderId
import br.com.fiap.automotivesaleshub.core.domain.payment.valueObjects.PaymentStatus
import br.com.fiap.automotivesaleshub.core.domain.vehicle.models.Vehicle
import br.com.fiap.automotivesaleshub.core.domain.vehicle.valueObjects.VehicleId
import br.com.fiap.automotivesaleshub.core.domain.vehicle.valueObjects.VehicleStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Instant
import java.util.*

class UpdatePaymentUseCase(
    private val paymentRepository: PaymentRepository,
    private val vehicleRepository: VehicleRepository,
    private val vehicleSalesService: VehicleSalesService,
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO),
) : UpdatePaymentDriverPort {
    override fun execute(input: UpdatePaymentInput) {
        val existingPayment = getPaymentOrFail(input.orderId)
        // TODO: use domain model update methods to ensure 'Tell, don't ask!'
        val paymentToUpdate =
            Payment(
                paymentId = existingPayment.paymentId,
                status = input.status,
                orderId = existingPayment.orderId,
                vehicleId = existingPayment.vehicleId,
                createdAt = existingPayment.createdAt,
                updatedAt = Instant.now(),
            )
        paymentRepository.update(paymentToUpdate)
        val vehicle = getVehicleOrFail(paymentToUpdate.vehicleId)
        if (input.status == PaymentStatus.APPROVED)
            return handleApprovedPayment(paymentToUpdate, vehicle)
        return handleRejectedPayment(paymentToUpdate, vehicle)
    }

    private fun getPaymentOrFail(orderId: String): Payment {
        val payment = paymentRepository.findByOrderId(OrderId(UUID.fromString(orderId)))
        if (payment == null)
            throw PaymentNotFoundException("Payment not found for orderId: $orderId.")
        return payment
    }

    private fun getVehicleOrFail(vehicleId: VehicleId) =
        vehicleRepository.findById(vehicleId)
            ?: throw VehicleNotFoundException("Vehicle not found: $vehicleId.")

    private fun handleApprovedPayment(payment: Payment, vehicle: Vehicle) {
        vehicle.updateStatus(VehicleStatus.SOLD)
        vehicleRepository.update(vehicle)
        coroutineScope.launch {
            vehicleSalesService.notifyPayment(payment.orderId.string(), PaymentStatus.APPROVED)
        }
        coroutineScope.launch { vehicleSalesService.updateVehicle(vehicle) }
    }

    private fun handleRejectedPayment(payment: Payment, vehicle: Vehicle) {
        vehicle.updateStatus(VehicleStatus.AVAILABLE)
        vehicleRepository.update(vehicle)
        coroutineScope.launch {
            vehicleSalesService.notifyPayment(payment.orderId.string(), PaymentStatus.REJECTED)
        }
        coroutineScope.launch { vehicleSalesService.updateVehicle(vehicle) }
    }
}
