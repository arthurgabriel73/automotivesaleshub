package br.com.fiap.automotivesaleshub.core.application.ports.driver.models.input

import br.com.fiap.automotivesaleshub.core.domain.vehicle.valueObjects.PriceCurrency
import br.com.fiap.automotivesaleshub.core.domain.vehicle.valueObjects.VehicleStatus

data class RegisterVehicleInput(
    val plate: String,
    val price: Long,
    val priceCurrency: PriceCurrency,
    val make: String,
    val model: String,
    val version: String,
    val yearFabrication: String,
    val yearModel: String,
    val kilometers: Int,
    val color: String,
    val status: VehicleStatus,
)
