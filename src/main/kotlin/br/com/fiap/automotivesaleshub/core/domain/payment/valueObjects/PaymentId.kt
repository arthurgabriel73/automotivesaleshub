package br.com.fiap.automotivesaleshub.core.domain.payment.valueObjects

import java.util.*

@JvmInline
value class PaymentId(val id: UUID) {
    fun string() = id.toString()
}
