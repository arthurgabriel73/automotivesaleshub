package br.com.fiap.automotivesaleshub.adapters.driven.api.paymentService

import br.com.fiap.automotivesaleshub.adapters.driven.api.dto.request.GenerateQRCodeRequest
import br.com.fiap.automotivesaleshub.core.application.ports.driven.PaymentService
import br.com.fiap.automotivesaleshub.core.domain.vehicle.models.Vehicle
import jakarta.inject.Named
import java.awt.image.BufferedImage

@Named
class PaymentServiceGateway(private val paymentServiceApi: PaymentServiceApi) : PaymentService {
    override fun generateQRCode(orderId: String, vehicle: Vehicle): BufferedImage {
        return paymentServiceApi
            .generateQRCode(GenerateQRCodeRequest(orderId, vehicle))
            .getQRCodeImage()
    }
}
