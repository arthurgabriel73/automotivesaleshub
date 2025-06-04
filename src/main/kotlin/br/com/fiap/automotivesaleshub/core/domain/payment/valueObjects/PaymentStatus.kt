package br.com.fiap.automotivesaleshub.core.domain.payment.valueObjects

enum class PaymentStatus {
    PENDING,
    APPROVED,
    REJECTED,
    CANCELLED;

    companion object
}
