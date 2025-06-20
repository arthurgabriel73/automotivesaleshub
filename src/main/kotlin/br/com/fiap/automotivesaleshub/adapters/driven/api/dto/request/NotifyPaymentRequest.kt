package br.com.fiap.automotivesaleshub.adapters.driven.api.dto.request

import br.com.fiap.automotivesaleshub.core.domain.payment.valueObjects.PaymentStatus

data class NotifyPaymentRequest(val orderId: String, val paymentStatus: PaymentStatus) {
    fun toJson(): Map<String, Any> =
        mapOf("orderId" to orderId, "paymentStatus" to paymentStatus.name)
}
