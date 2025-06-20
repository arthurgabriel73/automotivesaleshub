package br.com.fiap.automotivesaleshub.adapters.driver.webhook

import br.com.fiap.automotivesaleshub.adapters.driver.webhook.dto.UpdatePaymentRequest

interface UpdatePaymentRequestAdapter {
    fun adapt(request: UpdatePaymentRequest): String
}
