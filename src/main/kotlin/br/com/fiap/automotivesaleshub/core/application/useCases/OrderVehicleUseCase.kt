package br.com.fiap.automotivesaleshub.core.application.useCases

import br.com.fiap.automotivesaleshub.core.application.ports.driven.PaymentRepository
import br.com.fiap.automotivesaleshub.core.application.ports.driven.PaymentService
import br.com.fiap.automotivesaleshub.core.application.ports.driven.VehicleRepository
import br.com.fiap.automotivesaleshub.core.application.ports.driver.OrderVehicleDriverPort
import br.com.fiap.automotivesaleshub.core.application.ports.driver.models.input.OrderVehicleInput
import br.com.fiap.automotivesaleshub.core.application.ports.driver.models.output.OrderVehicleOutput
import br.com.fiap.automotivesaleshub.core.application.useCases.exceptions.VehicleOrderException
import br.com.fiap.automotivesaleshub.core.domain.payment.models.Payment
import br.com.fiap.automotivesaleshub.core.domain.payment.valueObjects.OrderId
import br.com.fiap.automotivesaleshub.core.domain.payment.valueObjects.PaymentId
import br.com.fiap.automotivesaleshub.core.domain.vehicle.models.Vehicle
import br.com.fiap.automotivesaleshub.core.domain.vehicle.valueObjects.VehicleId
import br.com.fiap.automotivesaleshub.core.domain.vehicle.valueObjects.VehicleStatus
import java.awt.image.BufferedImage
import java.time.Instant
import java.util.*

class OrderVehicleUseCase(
    val vehicleRepository: VehicleRepository,
    val paymentRepository: PaymentRepository,
    val paymentService: PaymentService,
) : OrderVehicleDriverPort {
    override fun execute(input: OrderVehicleInput): OrderVehicleOutput {
        try {
            val reservedVehicle = reserveVehicle(input.vehicleId)
            createPayment(input.orderId)
            val qrCode = getPaymentQRCode(input.orderId, reservedVehicle)
            return OrderVehicleOutput.success(qrCode)
        } catch (e: Exception) {
            throw VehicleOrderException("Failed to order vehicle: ${e.message}")
        }
    }

    private fun reserveVehicle(vehicleId: String): Vehicle {
        val vehicleId = VehicleId(UUID.fromString(vehicleId))
        val vehicle =
            vehicleRepository.getVehicleForUpdate(vehicleId)
                ?: throw Exception("Vehicle not found.")
        if (!vehicle.isAvailableForOrdering())
            throw Exception("Vehicle is not available for ordering.")
        // TODO: Implement reservation within the domain entity
        val reservedVehicle = vehicle.updateStatus(VehicleStatus.PROCESSING)
        return vehicleRepository.update(reservedVehicle)
    }

    private fun createPayment(orderId: String) {
        val payment =
            Payment(
                paymentId = PaymentId(UUID.randomUUID()),
                orderId = OrderId(UUID.fromString(orderId)),
                createdAt = Instant.now(),
            )
        paymentRepository.create(payment)
    }

    private fun getPaymentQRCode(orderId: String, vehicle: Vehicle): BufferedImage {
        try {
            return paymentService.generateQRCode(orderId, vehicle)
        } catch (e: Exception) {
            throw Exception("Failed to generate payment: ${e.message}")
        }
    }
}
