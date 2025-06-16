package br.com.fiap.automotivesaleshub.core.application.useCases.payment

import br.com.fiap.automotivesaleshub.core.application.ports.driven.PaymentRepository
import br.com.fiap.automotivesaleshub.core.application.ports.payment.driver.UpdatePaymentDriverPort
import br.com.fiap.automotivesaleshub.core.application.ports.payment.driver.models.input.UpdatePaymentInput
import br.com.fiap.automotivesaleshub.core.application.useCases.exceptions.PaymentNotFoundException
import br.com.fiap.automotivesaleshub.core.domain.payment.models.Payment
import br.com.fiap.automotivesaleshub.core.domain.payment.valueObjects.OrderId
import java.time.Instant
import java.util.*

class UpdatePaymentUseCase(val paymentRepository: PaymentRepository) : UpdatePaymentDriverPort {
    override fun execute(input: UpdatePaymentInput) {
        val existingPayment = getPaymentOrFail(input.orderId)
        // TODO: use domain model update methods to ensure 'Tell, don't ask!'
        val paymentToUpdate =
            Payment(
                paymentId = existingPayment.paymentId,
                status = input.status,
                orderId = existingPayment.orderId,
                createdAt = existingPayment.createdAt,
                updatedAt = Instant.now(),
            )
        paymentRepository.update(paymentToUpdate)
    }

    private fun getPaymentOrFail(orderId: String): Payment {
        val payment = paymentRepository.findByOrderId(OrderId(UUID.fromString(orderId)))
        if (payment == null)
            throw PaymentNotFoundException("Payment not found for orderId: $orderId.")
        return payment
    }
}
