package br.com.fiap.automotivesaleshub.core.application.ports.driven

import br.com.fiap.automotivesaleshub.core.domain.payment.models.Payment
import br.com.fiap.automotivesaleshub.core.domain.payment.valueObjects.PaymentId
import java.util.*

interface PaymentRepository {
    fun create(payment: Payment): PaymentId

    fun getByOrder(order: UUID): Payment?

    fun update(payment: Payment)
}
