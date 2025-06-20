package br.com.fiap.automotivesaleshub.adapters.driven.persistence.payment

import br.com.fiap.automotivesaleshub.core.domain.payment.models.Payment
import br.com.fiap.automotivesaleshub.core.domain.payment.valueObjects.OrderId
import br.com.fiap.automotivesaleshub.core.domain.payment.valueObjects.PaymentId
import br.com.fiap.automotivesaleshub.core.domain.payment.valueObjects.PaymentStatus
import br.com.fiap.automotivesaleshub.core.domain.vehicle.valueObjects.VehicleId
import jakarta.persistence.*
import java.time.Instant
import java.util.*

@Entity
@Table(name = "payments")
data class PaymentEntity(
    @Id val paymentId: UUID,
    @Enumerated(EnumType.STRING) val status: PaymentStatus,
    val orderId: UUID,
    val vehicleId: UUID,
    val createdAt: Instant? = null,
    val updatedAt: Instant? = null,
) {
    fun toDomain() =
        Payment(
            paymentId = PaymentId(paymentId),
            status = status,
            orderId = OrderId(orderId),
            vehicleId = VehicleId(vehicleId),
            createdAt = createdAt,
            updatedAt = updatedAt,
        )

    companion object {
        fun fromDomain(payment: Payment): PaymentEntity {
            return PaymentEntity(
                paymentId = payment.paymentId.id,
                status = payment.status,
                orderId = payment.orderId.id,
                vehicleId = payment.vehicleId.id,
                createdAt = payment.createdAt,
                updatedAt = payment.updatedAt,
            )
        }
    }
}
