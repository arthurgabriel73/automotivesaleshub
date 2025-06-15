package br.com.fiap.automotivesaleshub.core.application.ports.payment.driver

import br.com.fiap.automotivesaleshub.core.application.ports.payment.driver.models.input.UpdatePaymentInput

interface UpdatePaymentDriverPort {
    fun execute(input: UpdatePaymentInput)
}
