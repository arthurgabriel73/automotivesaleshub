package br.com.fiap.automotivesaleshub.core.domain.vehicle.exceptions

import br.com.fiap.automotivesaleshub.core.domain.exceptions.DomainException

data class InvalidVehicleStatusException(
    override val message: String = "Invalid vehicle status.",
    override val cause: Throwable? = null,
) : DomainException(message, cause)
