package br.com.fiap.automotivesaleshub.adapters.driver.webhook

import br.com.fiap.automotivesaleshub.adapters.driver.webhook.dto.UpdatePaymentRequest
import br.com.fiap.automotivesaleshub.core.application.ports.payment.driver.UpdatePaymentDriverPort
import br.com.fiap.automotivesaleshub.core.application.ports.payment.driver.models.input.UpdatePaymentInput
import jakarta.transaction.Transactional
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/webhook/payments")
class PaymentWebhookController(
    private val updatePaymentDriverPort: UpdatePaymentDriverPort,
    private val updatePaymentRequestAdapter: UpdatePaymentRequestAdapter,
) {

    @PostMapping("{orderId}")
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    fun updatePayment(
        @PathVariable(name = "orderId", required = true) orderId: String,
        @RequestBody request: UpdatePaymentRequest,
    ) {
        val paymentStatus = updatePaymentRequestAdapter.adapt(request)
        val input = UpdatePaymentInput(orderId, paymentStatus)
        updatePaymentDriverPort.execute(input)
    }
}
