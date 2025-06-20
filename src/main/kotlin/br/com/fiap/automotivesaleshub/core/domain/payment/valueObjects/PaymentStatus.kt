package br.com.fiap.automotivesaleshub.core.domain.payment.valueObjects

import br.com.fiap.automotivesaleshub.core.domain.payment.exceptions.InvalidPaymentStatusException

enum class PaymentStatus {
    PENDING,
    APPROVED,
    REJECTED,
    CANCELLED;

    companion object {
        fun fromString(status: String): PaymentStatus {
            return when (status.uppercase()) {
                "PENDING" -> PENDING
                "APPROVED" -> APPROVED
                "REJECTED" -> REJECTED
                "CANCELLED" -> CANCELLED
                else -> throw InvalidPaymentStatusException("Invalid payment status: $status")
            }
        }
    }
}
