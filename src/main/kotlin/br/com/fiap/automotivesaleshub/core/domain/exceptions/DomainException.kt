package br.com.fiap.automotivesaleshub.core.domain.exceptions

open class DomainException(
    override val message: String? = null,
    override val cause: Throwable? = null,
) : Exception(message, cause)
