package br.com.fiap.automotivesaleshub.core.application.ports.driver.models.output

import java.awt.image.BufferedImage

data class PurchaseVehicleOutput(
    val result: Boolean,
    val message: String,
    val qrCode: BufferedImage,
) {
    companion object {
        fun success(qrCode: BufferedImage): PurchaseVehicleOutput =
            PurchaseVehicleOutput(
                true,
                "Vehicle ordered successfully, please use the QR code to complete the payment.",
                qrCode,
            )
    }
}
