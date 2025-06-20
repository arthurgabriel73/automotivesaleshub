package br.com.fiap.automotivesaleshub.adapters.driver.webhook.adapters

import br.com.fiap.automotivesaleshub.adapters.driver.webhook.UpdatePaymentRequestAdapter
import br.com.fiap.automotivesaleshub.adapters.driver.webhook.dto.UpdatePaymentRequest
import br.com.fiap.automotivesaleshub.core.domain.payment.valueObjects.PaymentStatus

class MercadoPagoAdapter : UpdatePaymentRequestAdapter {
    override fun adapt(request: UpdatePaymentRequest): PaymentStatus {
        val receivedTopic = request.topic
        if (receivedTopic == "payment") return PaymentStatus.APPROVED
        return PaymentStatus.REJECTED
    }
}
