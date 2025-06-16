package br.com.fiap.automotivesaleshub.core.domain.payment.valueObjects

import java.util.*

@JvmInline
value class OrderId(val id: UUID) {
    fun string() = id.toString()
}
