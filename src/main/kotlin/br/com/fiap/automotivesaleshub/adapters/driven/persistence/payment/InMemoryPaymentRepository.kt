package br.com.fiap.automotivesaleshub.adapters.driven.persistence.payment

import br.com.fiap.automotivesaleshub.core.application.ports.driven.PaymentRepository
import br.com.fiap.automotivesaleshub.core.domain.payment.models.Payment
import br.com.fiap.automotivesaleshub.core.domain.payment.valueObjects.OrderId

class InMemoryPaymentRepository : PaymentRepository {
    val payments = mutableListOf<Payment>()

    override fun create(payment: Payment): Payment {
        payments.add(toNewInstance(payment))
        return toNewInstance(payment)
    }

    override fun findByOrderId(orderId: OrderId): Payment? {
        return payments.find { it.orderId == orderId }?.let { toNewInstance(it) }
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
            orderId = payment.orderId,
            vehicleId = payment.vehicleId,
            createdAt = payment.createdAt,
            updatedAt = payment.updatedAt,
        )
    }
}
