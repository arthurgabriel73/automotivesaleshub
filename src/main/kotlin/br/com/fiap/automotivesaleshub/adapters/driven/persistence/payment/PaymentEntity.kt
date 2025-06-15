package br.com.fiap.automotivesaleshub.adapters.driven.persistence.payment

import br.com.fiap.automotivesaleshub.core.domain.payment.models.Payment
import br.com.fiap.automotivesaleshub.core.domain.payment.valueObjects.PaymentId
import br.com.fiap.automotivesaleshub.core.domain.payment.valueObjects.PaymentStatus
import jakarta.persistence.*
import java.time.Instant
import java.util.*

@Entity
@Table(name = "payments")
data class PaymentEntity(
    @Id val paymentId: UUID,
    @Enumerated(EnumType.STRING) val status: PaymentStatus,
    val order: UUID,
    val createdAt: Instant? = null,
    val updatedAt: Instant? = null,
) {
    fun toDomain() =
        Payment(
            paymentId = PaymentId(paymentId),
            status = status,
            order = order,
            createdAt = createdAt,
            updatedAt = updatedAt,
        )

    companion object {
        fun fromDomain(payment: Payment): PaymentEntity {
            return PaymentEntity(
                paymentId = payment.paymentId.id,
                status = payment.status,
                order = payment.order,
                createdAt = payment.createdAt,
                updatedAt = payment.updatedAt,
            )
        }
    }
}
