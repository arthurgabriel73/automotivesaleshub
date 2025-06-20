package br.com.fiap.automotivesaleshub.adapters.driven.api.paymentService

import br.com.fiap.automotivesaleshub.adapters.driven.api.dto.request.GenerateQRCodeRequest
import br.com.fiap.automotivesaleshub.adapters.driven.api.dto.response.GenerateQRCodeResponse
import jakarta.inject.Named

@Named
class MercadoPagoPaymentServiceApi : PaymentServiceApi {

    override fun generateQRCode(request: GenerateQRCodeRequest): GenerateQRCodeResponse {
        // TODO: Implement the actual logic to generate a QR code using Mercado Pago API.
        return GenerateQRCodeResponse(
            qrCode = "https://www.mercadopago.com.br/qr-code?data=example"
        )
    }
}
