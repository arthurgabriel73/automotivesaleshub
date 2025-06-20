package br.com.fiap.automotivesaleshub.core.application.ports.payment.driver.models.input

import br.com.fiap.automotivesaleshub.core.domain.payment.valueObjects.PaymentStatus

data class UpdatePaymentInput(
    val orderId: String,
    val vehicleId: String,
    val status: PaymentStatus,
)
