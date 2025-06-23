package br.com.fiap.automotivesaleshub.configuration

import br.com.fiap.automotivesaleshub.core.application.useCases.exceptions.PaymentNotFoundException
import br.com.fiap.automotivesaleshub.core.application.useCases.exceptions.VehicleIsAlreadyRegisteredException
import br.com.fiap.automotivesaleshub.core.application.useCases.exceptions.VehicleNotFoundException
import br.com.fiap.automotivesaleshub.core.application.useCases.exceptions.VehiclePurchaseException
import br.com.fiap.automotivesaleshub.core.domain.payment.exceptions.InvalidPaymentStatusException
import br.com.fiap.automotivesaleshub.core.domain.vehicle.exceptions.InvalidVehicleStatusException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class CustomExceptionHandler : ResponseEntityExceptionHandler() {

    override fun handleMethodArgumentNotValid(
        exception: MethodArgumentNotValidException,
        headers: HttpHeaders,
        statusCode: HttpStatusCode,
        request: WebRequest,
    ): ResponseEntity<Any>? {

        val errors =
            exception.bindingResult.fieldErrors.map {
                mapOf(
                    "path" to listOf(it.field),
                    "message" to (it.defaultMessage ?: "Invalid argument"),
                )
            }

        val errorBody =
            mapOf(
                "status" to HttpStatus.BAD_REQUEST.value(),
                "error" to "Validation failed",
                "issues" to errors,
            )

        return ResponseEntity(errorBody, headers, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(VehicleIsAlreadyRegisteredException::class)
    fun handleVehicleIsAlreadyRegisteredException(
        exception: VehicleIsAlreadyRegisteredException
    ): ResponseEntity<ErrorMessage> {
        val errorMessage = ErrorMessage(status = HttpStatus.CONFLICT, error = exception.message)
        return ResponseEntity(errorMessage, HttpHeaders(), errorMessage.status)
    }

    @ExceptionHandler(VehicleNotFoundException::class)
    fun handleVehicleNotFoundException(
        exception: VehicleNotFoundException
    ): ResponseEntity<ErrorMessage> {
        val errorMessage = ErrorMessage(status = HttpStatus.NOT_FOUND, error = exception.message)
        return ResponseEntity(errorMessage, HttpHeaders(), errorMessage.status)
    }

    @ExceptionHandler(PaymentNotFoundException::class)
    fun handlePaymentNotFoundException(
        exception: PaymentNotFoundException
    ): ResponseEntity<ErrorMessage> {
        val errorMessage = ErrorMessage(status = HttpStatus.NOT_FOUND, error = exception.message)
        return ResponseEntity(errorMessage, HttpHeaders(), errorMessage.status)
    }

    @ExceptionHandler(InvalidPaymentStatusException::class)
    fun handleInvalidPaymentStatusException(
        exception: InvalidPaymentStatusException
    ): ResponseEntity<ErrorMessage> {
        val errorMessage = ErrorMessage(status = HttpStatus.BAD_REQUEST, error = exception.message)
        return ResponseEntity(errorMessage, HttpHeaders(), errorMessage.status)
    }

    @ExceptionHandler(VehiclePurchaseException::class)
    fun handleVehicleOrderException(
        exception: VehiclePurchaseException
    ): ResponseEntity<ErrorMessage> {
        val errorMessage =
            ErrorMessage(status = HttpStatus.NOT_ACCEPTABLE, error = exception.message)
        return ResponseEntity(errorMessage, HttpHeaders(), errorMessage.status)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(
        exception: IllegalArgumentException
    ): ResponseEntity<ErrorMessage> {
        val errorMessage =
            ErrorMessage(
                status = HttpStatus.BAD_REQUEST,
                error = exception.message ?: "Invalid argument provided",
            )
        return ResponseEntity(errorMessage, HttpHeaders(), errorMessage.status)
    }

    @ExceptionHandler(InvalidVehicleStatusException::class)
    fun handleInvalidVehicleStatusException(
        exception: InvalidVehicleStatusException
    ): ResponseEntity<ErrorMessage> {
        val errorMessage = ErrorMessage(status = HttpStatus.BAD_REQUEST, error = exception.message)
        return ResponseEntity(errorMessage, HttpHeaders(), errorMessage.status)
    }
}
