package br.com.fiap.automotivesaleshub.adapters.driver.rest

import br.com.fiap.automotivesaleshub.core.application.ports.driver.models.RegisterVehicleDriverPort
import br.com.fiap.automotivesaleshub.core.application.ports.driver.models.input.RegisterVehicleInput
import br.com.fiap.automotivesaleshub.core.application.ports.driver.models.output.RegisterVehicleOutput
import jakarta.transaction.Transactional
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/vehicles")
class VehicleController(private val registerVehicleDriverPort: RegisterVehicleDriverPort) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    fun registerVehicle(
        @Validated @RequestBody input: RegisterVehicleInput
    ): RegisterVehicleOutput {
        return registerVehicleDriverPort.execute(input)
    }
}
