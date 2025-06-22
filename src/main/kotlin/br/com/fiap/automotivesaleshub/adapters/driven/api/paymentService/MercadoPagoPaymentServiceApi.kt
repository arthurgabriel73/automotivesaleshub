package br.com.fiap.automotivesaleshub.adapters.driven.api.paymentService

import br.com.fiap.automotivesaleshub.adapters.driven.api.dto.request.GenerateQRCodeRequest
import br.com.fiap.automotivesaleshub.adapters.driven.api.dto.response.GenerateQRCodeResponse
import br.com.fiap.automotivesaleshub.adapters.driven.api.paymentService.dto.request.OrderPaymentRequest
import br.com.fiap.automotivesaleshub.adapters.driven.api.paymentService.dto.request.OrderPaymentRequestItem
import br.com.fiap.automotivesaleshub.adapters.driven.api.paymentService.dto.response.OrderPaymentResponse
import jakarta.inject.Named
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.web.client.RestTemplate

@Named
class MercadoPagoPaymentServiceApi : PaymentServiceApi {

    private var restTemplate: RestTemplate = RestTemplate()

    @Value("\${mercado.pago.access.token}") private val API_ACCESS_TOKEN: String? = null

    @Value("\${mercado.pago.api.criacao.qr.code.url}") private val API_CODIGO_QR_URL: String? = null

    @Value("\${mercado.pago.api.vendedor.id}") private val API_ID_VENDEDOR: String? = null

    @Value("\${mercado.pago.caixa.url}") private val API_URL_CAIXA: String? = null

    @Value("\${mercado.pago.api.caixa.id.externo}") private val API_CAIXA_ID_EXTERNO: String? = null

    @Value("\${server.notification.url}") private val SERVER_NOTIFICATION_URL: String? = null

    override fun generateQRCode(request: GenerateQRCodeRequest): GenerateQRCodeResponse {
        generateUrl()
        val item =
            OrderPaymentRequestItem(
                id = request.vehicle.vehicleId.string(),
                title = request.vehicle.specifications.model,
                description = "Vehicle",
                unitMeasure = "unit",
                category = "vehicle",
                unitPrice = request.vehicle.price.amount / 100.0, // Assuming price is in cents
                quantity = 1,
                totalAmount =
                    request.vehicle.price.amount.toBigDecimal() /
                        100.toBigDecimal(), // Assuming price is in cents
            )

        val orderPaymentRequest =
            OrderPaymentRequest(
                externalReference = request.orderId,
                title = "Payment for Vehicle",
                notificationUrl =
                    SERVER_NOTIFICATION_URL + "/v1/webhook/payments" + request.orderId,
                totalAmount =
                    (request.vehicle.price.amount / 100)
                        .toBigDecimal(), // Assuming price is in cents
                description = "Payment for vehicle with ID: ${request.vehicle.vehicleId.string()}",
                items = listOf(item),
            )

        val response =
            restTemplate.postForEntity(
                generateUrl(),
                HttpEntity(orderPaymentRequest.toJson(), generateHeaders()),
                OrderPaymentResponse::class.java,
            )
        return GenerateQRCodeResponse(qrCode = response.body?.qr_data)
    }

    private fun generateUrl(): String {
        return "$API_CODIGO_QR_URL/$API_ID_VENDEDOR/$API_URL_CAIXA/$API_CAIXA_ID_EXTERNO/qrs"
    }

    private fun generateHeaders(): HttpHeaders {
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        headers.setBearerAuth(API_ACCESS_TOKEN ?: "")
        return headers
    }
}
