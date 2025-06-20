package br.com.fiap.automotivesaleshub.adapters.driven.api.dto.response

import java.awt.image.BufferedImage

data class GenerateQRCodeResponse(val qrCode: String) {
    fun getQRCodeImage(): BufferedImage {
        return qrCode.toByteArray().inputStream().use { inputStream ->
            javax.imageio.ImageIO.read(inputStream)
        }
    }
}
