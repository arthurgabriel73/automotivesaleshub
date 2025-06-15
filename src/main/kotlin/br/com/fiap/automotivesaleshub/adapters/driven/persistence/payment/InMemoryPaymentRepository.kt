package br.com.fiap.automotivesaleshub.adapters.driven.persistence.payment

import br.com.fiap.automotivesaleshub.core.application.ports.driven.PaymentRepository
import br.com.fiap.automotivesaleshub.core.domain.payment.models.Payment
import br.com.fiap.automotivesaleshub.core.domain.payment.valueObjects.PaymentId
import java.util.*

class InMemoryPaymentRepository : PaymentRepository {
    val payments = mutableListOf<Payment>()

    override fun create(payment: Payment): PaymentId {
        payments.add(toNewInstance(payment))
        return toNewInstance(payment).paymentId
    }

    override fun getByOrder(order: UUID): Payment? {
        return toNewInstance(payments.find { it.order == order } ?: return null)
    }

    override fun update(payment: Payment) {
        val index = payments.indexOfFirst { it.paymentId == payment.paymentId }
        if (index == -1)
            throw IllegalArgumentException("Payment with id ${payment.paymentId} not found")
        payments[index] = toNewInstance(payment)
    }

    private fun toNewInstance(payment: Payment): Payment {
        return Payment(
            paymentId = payment.paymentId,
            status = payment.status,
            order = payment.order,
            createdAt = payment.createdAt,
            updatedAt = payment.updatedAt,
        )
    }
}
