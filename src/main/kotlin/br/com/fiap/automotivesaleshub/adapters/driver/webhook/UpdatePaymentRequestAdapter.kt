package br.com.fiap.automotivesaleshub.adapters.driver.webhook

import br.com.fiap.automotivesaleshub.adapters.driver.webhook.dto.UpdatePaymentRequest
import br.com.fiap.automotivesaleshub.core.domain.payment.valueObjects.PaymentStatus

interface UpdatePaymentRequestAdapter {
    fun adapt(request: UpdatePaymentRequest): PaymentStatus
}
