package br.com.fiap.automotivesaleshub.adapters.driven.api.paymentService.dto.request

import java.math.BigDecimal

data class OrderPaymentRequestItem(
    val id: String,
    val category: String,
    val title: String,
    val description: String,
    val unitPrice: Double,
    val quantity: Int,
    val unitMeasure: String,
    val totalAmount: BigDecimal,
) {
    fun toJson(): Map<String, Any> {
        return mapOf(
            "id" to id,
            "category" to category,
            "title" to title,
            "description" to description,
            "unit_price" to unitPrice,
            "quantity" to quantity,
            "unit_measure" to unitMeasure,
            "total_amount" to totalAmount,
        )
    }
}
