package br.com.fiap.automotivesaleshub.core.application.ports.driver.models.input

import br.com.fiap.automotivesaleshub.core.domain.vehicle.valueObjects.PriceCurrency

data class UpdateVehicleInput(
    val plate: String,
    val price: Long,
    val priceCurrency: PriceCurrency,
    val kilometers: Int,
    val color: String,
)
