package br.com.fiap.automotivesaleshub.core.domain.payment.exceptions

import br.com.fiap.automotivesaleshub.core.domain.exceptions.DomainException

data class InvalidPaymentStatusException(
    override val message: String? = null,
    override val cause: Throwable? = null,
) : DomainException(message, cause)
