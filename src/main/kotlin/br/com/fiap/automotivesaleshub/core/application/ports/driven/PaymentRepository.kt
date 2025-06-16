package br.com.fiap.automotivesaleshub.core.application.ports.driven

import br.com.fiap.automotivesaleshub.core.domain.payment.models.Payment
import br.com.fiap.automotivesaleshub.core.domain.payment.valueObjects.OrderId

interface PaymentRepository {
    fun create(payment: Payment): Payment

    fun findByOrderId(orderId: OrderId): Payment?

    fun update(payment: Payment)
}
