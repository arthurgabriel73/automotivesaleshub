package br.com.fiap.automotivesaleshub.core.domain.vehicle.exceptions

open class CustomException(
    override val message: String? = null,
    override val cause: Throwable? = null,
) : Exception(message, cause)

data class InvalidVehicleStatusException(
    override val message: String? = null,
    override val cause: Throwable? = null,
) : CustomException(message, cause)

data class InvalidPaymentStatusException(
    override val message: String? = null,
    override val cause: Throwable? = null,
) : CustomException(message, cause)
