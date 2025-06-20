package br.com.fiap.automotivesaleshub.adapters.driven.api.paymentService

import br.com.fiap.automotivesaleshub.adapters.driven.api.dto.request.GenerateQRCodeRequest
import br.com.fiap.automotivesaleshub.adapters.driven.api.dto.response.GenerateQRCodeResponse

interface PaymentServiceApi {
    fun generateQRCode(request: GenerateQRCodeRequest): GenerateQRCodeResponse
}
