package br.com.fiap.automotivesaleshub.core.application.ports.payment.driver.models.input

import br.com.fiap.automotivesaleshub.core.domain.payment.valueObjects.PaymentStatus
import java.util.*

data class UpdatePaymentInput(val orderId: UUID, val status: PaymentStatus)
