package br.com.fiap.automotivesaleshub.core.domain.vehicle.exceptions

open class DomainException(
    override val message: String? = null,
    override val cause: Throwable? = null,
) : Exception(message, cause)

data class InvalidVehicleStatusException(
    override val message: String? = null,
    override val cause: Throwable? = null,
) : DomainException(message, cause)

data class InvalidPaymentStatusException(
    override val message: String? = null,
    override val cause: Throwable? = null,
) : DomainException(message, cause)
