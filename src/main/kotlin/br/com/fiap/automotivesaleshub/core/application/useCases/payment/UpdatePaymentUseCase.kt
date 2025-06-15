package br.com.fiap.automotivesaleshub.core.application.useCases.payment

import br.com.fiap.automotivesaleshub.core.application.ports.driven.PaymentRepository
import br.com.fiap.automotivesaleshub.core.application.ports.payment.driver.UpdatePaymentDriverPort
import br.com.fiap.automotivesaleshub.core.application.ports.payment.driver.models.input.UpdatePaymentInput
import br.com.fiap.automotivesaleshub.core.application.useCases.exceptions.PaymentNotFoundException
import br.com.fiap.automotivesaleshub.core.domain.payment.models.Payment
import java.time.Instant
import java.util.*

class UpdatePaymentUseCase(val paymentRepository: PaymentRepository) : UpdatePaymentDriverPort {
    override fun execute(input: UpdatePaymentInput) {
        val existingPayment = getPaymentOrFail(input.order)
        // TODO: use domain model update methods to ensure 'Tell, don't ask!'
        val paymentToUpdate =
            Payment(
                paymentId = existingPayment.paymentId,
                status = input.status,
                order = existingPayment.order,
                createdAt = existingPayment.createdAt,
                updatedAt = Instant.now(),
            )
        paymentRepository.update(paymentToUpdate)
    }

    private fun getPaymentOrFail(order: String): Payment {
        val payment = paymentRepository.getByOrder(UUID.fromString(order))
        if (payment == null) throw PaymentNotFoundException("Payment not found for order: $order.")
        return payment
    }
}
