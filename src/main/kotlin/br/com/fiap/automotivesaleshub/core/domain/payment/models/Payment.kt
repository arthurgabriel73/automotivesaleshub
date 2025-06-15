package br.com.fiap.automotivesaleshub.core.domain.payment.models

import br.com.fiap.automotivesaleshub.core.domain.payment.exceptions.InvalidPaymentStatusException
import br.com.fiap.automotivesaleshub.core.domain.payment.valueObjects.PaymentId
import br.com.fiap.automotivesaleshub.core.domain.payment.valueObjects.PaymentStatus
import java.time.Instant
import java.util.*

class Payment(
    paymentId: PaymentId = PaymentId(),
    status: PaymentStatus = PaymentStatus.PENDING,
    order: UUID,
    createdAt: Instant? = null,
    updatedAt: Instant? = null,
) {
    var paymentId = paymentId
        private set

    var status = status
        private set

    var order = order
        private set

    var createdAt = createdAt
        private set

    var updatedAt = updatedAt
        private set

    @Throws(InvalidPaymentStatusException::class)
    fun updateStatus(newStatus: PaymentStatus): Payment {
        if (status != PaymentStatus.PENDING)
            throw InvalidPaymentStatusException("Status $status is not PENDING")
        status = newStatus
        updatedAt = Instant.now()
        return this
    }
}
