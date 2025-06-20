package br.com.fiap.automotivesaleshub.core.domain.vehicle.valueObjects

import br.com.fiap.automotivesaleshub.core.domain.payment.exceptions.InvalidPriceCurrencyException

enum class PriceCurrency {
    EUR,
    USD,
    BRL;

    companion object {
        fun fromString(priceCurrency: String): PriceCurrency {
            return when (priceCurrency.uppercase()) {
                "EUR" -> EUR
                "USD" -> USD
                "BRL" -> BRL
                else ->
                    throw InvalidPriceCurrencyException("Invalid price currency: $priceCurrency")
            }
        }
    }
}
