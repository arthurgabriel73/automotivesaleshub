package br.com.fiap.automotivesaleshub.core.application.useCases.exceptions

open class ApplicationException(
    override val message: String = "An application error occurred.",
    override val cause: Throwable? = null,
) : Exception(message, cause)

data class VehicleIsAlreadyRegisteredException(
    override val message: String = "Vehicle is already registered.",
    override val cause: Throwable? = null,
) : ApplicationException(message, cause)

data class VehicleNotFoundException(
    override val message: String = "Vehicle not found.",
    override val cause: Throwable? = null,
) : ApplicationException(message, cause)

data class PaymentNotFoundException(
    override val message: String = "Payment not found.",
    override val cause: Throwable? = null,
) : ApplicationException(message, cause)

data class VehicleOrderException(
    override val message: String = "Failed to order vehicle.",
    override val cause: Throwable? = null,
) : ApplicationException(message, cause)
