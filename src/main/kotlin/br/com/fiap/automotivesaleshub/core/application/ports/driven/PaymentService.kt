package br.com.fiap.automotivesaleshub.core.application.ports.driven

import br.com.fiap.automotivesaleshub.core.domain.vehicle.models.Vehicle
import java.awt.image.BufferedImage

interface PaymentService {
    fun generateQRCode(orderId: String, vehicle: Vehicle): BufferedImage
}
