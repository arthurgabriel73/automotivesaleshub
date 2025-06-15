package br.com.fiap.automotivesaleshub.core.application.ports.driven

import br.com.fiap.automotivesaleshub.core.domain.payment.models.Payment
import java.util.*

interface PaymentRepository {
    fun create(payment: Payment): Payment

    fun findByOrder(order: UUID): Payment?

    fun update(payment: Payment)
}
