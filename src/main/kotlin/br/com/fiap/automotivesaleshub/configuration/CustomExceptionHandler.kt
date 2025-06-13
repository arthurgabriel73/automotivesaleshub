package br.com.fiap.automotivesaleshub.configuration

import br.com.fiap.automotivesaleshub.core.application.useCases.exceptions.VehicleIsAlreadyRegisteredException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class CustomExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(VehicleIsAlreadyRegisteredException::class)
    fun handleVehicleIsAlreadyRegisteredException(
        exception: VehicleIsAlreadyRegisteredException
    ): ResponseEntity<ErrorMessage> {
        val errorMessage = ErrorMessage(status = HttpStatus.CONFLICT, error = exception.message)
        return ResponseEntity(errorMessage, HttpHeaders(), errorMessage.status)
    }
}
