package br.com.fiap.automotivesaleshub.adapters.driver.rest.dto

import br.com.fiap.automotivesaleshub.core.application.ports.driver.models.input.UpdateVehicleInput
import br.com.fiap.automotivesaleshub.core.domain.vehicle.valueObjects.PriceCurrency
import br.com.fiap.automotivesaleshub.core.domain.vehicle.valueObjects.VehicleStatus
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.PositiveOrZero
import org.hibernate.validator.constraints.UUID

data class UpdateVehicleRequest(
    @field:NotBlank
    @field:UUID(message = "Vehicle ID is required and must be a valid UUID")
    val vehicleId: String,
    @field:NotBlank(message = "Plate is required")
    @field:Pattern(
        regexp = "^[A-Z]{3}-[0-9][A-Z][0-9]{2}|[A-Z]{3}-[0-9]{4}$",
        message = "Invalid plate format",
    )
    val plate: String,
    @field:Positive(message = "Price must be a positive number") val price: Long,
    @field:NotBlank(message = "Price currency is required") val priceCurrency: String,
    @field:NotBlank(message = "Make is required") val make: String,
    @field:NotBlank(message = "Model is required") val model: String,
    @field:NotBlank(message = "Version is required") val version: String,
    @field:NotBlank(message = "Year of fabrication is required") val yearFabrication: String,
    @field:NotBlank(message = "Year model is required") val yearModel: String,
    @field:PositiveOrZero(message = "Kilometers must be zero or a positive number")
    val kilometers: Int,
    @field:NotBlank(message = "Color is required") val color: String,
    @field:NotBlank(message = "Status is required") val status: String,
) {
    fun toInput() =
        UpdateVehicleInput(
            vehicleId = vehicleId,
            plate = plate,
            price = price,
            priceCurrency = priceCurrency.let { PriceCurrency.valueOf(it.uppercase()) },
            make = make,
            model = model,
            version = version,
            yearFabrication = yearFabrication,
            yearModel = yearModel,
            kilometers = kilometers,
            color = color,
            status = status.let { VehicleStatus.valueOf(it.uppercase()) },
        )
}
