package br.com.fiap.automotivesaleshub.core.application.useCases.exceptions

open class ApplicationException(
    override val message: String = "An application error occurred.",
    override val cause: Throwable? = null,
) : Exception(message, cause)

data class VehicleIsAlreadyRegisteredException(
    override val message: String = "Vehicle is already registered.",
    override val cause: Throwable? = null,
) : ApplicationException(message, cause)
