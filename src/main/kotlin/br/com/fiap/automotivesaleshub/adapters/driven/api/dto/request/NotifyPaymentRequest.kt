package br.com.fiap.automotivesaleshub.adapters.driven.api.dto.request

import br.com.fiap.automotivesaleshub.core.domain.payment.valueObjects.PaymentStatus

data class NotifyPaymentRequest(val orderId: String, val paymentStatus: PaymentStatus) {
    fun toMap(): Map<String, Any> {
        return mapOf("orderId" to orderId, "paymentStatus" to paymentStatus.name)
    }
}
