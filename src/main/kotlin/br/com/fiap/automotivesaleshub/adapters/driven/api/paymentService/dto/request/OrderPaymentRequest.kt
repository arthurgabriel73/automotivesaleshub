package br.com.fiap.automotivesaleshub.adapters.driven.api.paymentService.dto.request

import java.math.BigDecimal

data class OrderPaymentRequest(
    val externalReference: String,
    val title: String,
    val notificationUrl: String,
    val totalAmount: BigDecimal,
    val description: String,
    val items: List<OrderPaymentRequestItem>,
) {
    fun toJson(): Map<String, Any> {
        return mapOf(
            "external_reference" to externalReference,
            "title" to title,
            "notification_url" to notificationUrl,
            "total_amount" to totalAmount,
            "description" to description,
            "items" to items.map { it.toJson() },
        )
    }
}
