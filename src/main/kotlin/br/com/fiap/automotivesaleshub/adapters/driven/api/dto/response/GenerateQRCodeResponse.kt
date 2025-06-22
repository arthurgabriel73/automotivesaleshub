package br.com.fiap.automotivesaleshub.adapters.driven.api.dto.response

import com.google.zxing.BarcodeFormat
import com.google.zxing.client.j2se.MatrixToImageWriter
import com.google.zxing.qrcode.QRCodeWriter
import java.awt.image.BufferedImage

data class GenerateQRCodeResponse(val qrCode: String?) {
    fun getQRCodeImage(): BufferedImage {
        val barcodeWriter = QRCodeWriter()
        val bitMatrix = barcodeWriter.encode(qrCode, BarcodeFormat.QR_CODE, 200, 200)
        return MatrixToImageWriter.toBufferedImage(bitMatrix)
    }
}
