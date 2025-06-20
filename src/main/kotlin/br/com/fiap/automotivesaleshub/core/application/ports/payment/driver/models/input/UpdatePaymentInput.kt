package br.com.fiap.automotivesaleshub.core.application.ports.payment.driver.models.input

import br.com.fiap.automotivesaleshub.core.domain.payment.valueObjects.PaymentStatus

data class UpdatePaymentInput(val orderId: String, private val statusName: String) {
    val status = PaymentStatus.fromString(statusName)

    init {
        require(orderId.isNotBlank()) { "Order ID must not be blank" }
        require(statusName.isNotBlank()) { "Payment status must not be blank" }
    }
}
