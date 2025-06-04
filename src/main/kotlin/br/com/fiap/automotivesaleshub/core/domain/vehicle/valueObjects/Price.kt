package br.com.fiap.automotivesaleshub.core.domain.vehicle.valueObjects

data class Price(val amount: Long = 0, val currency: PriceCurrency = PriceCurrency.BRL)
