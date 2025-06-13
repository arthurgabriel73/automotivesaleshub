package br.com.fiap.automotivesaleshub.configuration

import org.springframework.http.HttpStatus

data class ErrorMessage(val status: HttpStatus, val error: String)
