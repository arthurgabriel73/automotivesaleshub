package br.com.fiap.automotivesaleshub.core.domain.payment.exceptions

import br.com.fiap.automotivesaleshub.core.domain.exceptions.DomainException

data class InvalidPaymentStatusException(
    override val message: String = "Invalid payment status.",
    override val cause: Throwable? = null,
) : DomainException(message, cause)

data class InvalidPriceCurrencyException(
    override val message: String = "Invalid price currency.",
    override val cause: Throwable? = null,
) : DomainException(message, cause)
