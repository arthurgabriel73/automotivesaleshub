package br.com.fiap.automotivesaleshub.core.domain.vehicle.valueObjects

data class Specifications(
    val make: String,
    val model: String,
    val version: String,
    val yearFabrication: String,
    val yearModel: String,
    val kilometers: Int,
    val color: String,
)
