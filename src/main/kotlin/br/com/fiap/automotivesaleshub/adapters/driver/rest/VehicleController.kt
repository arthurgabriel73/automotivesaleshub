package br.com.fiap.automotivesaleshub.adapters.driver.rest

import br.com.fiap.automotivesaleshub.core.application.ports.driver.PurchaseVehicleDriverPort
import br.com.fiap.automotivesaleshub.core.application.ports.driver.RegisterVehicleDriverPort
import br.com.fiap.automotivesaleshub.core.application.ports.driver.UpdateVehicleDriverPort
import br.com.fiap.automotivesaleshub.core.application.ports.driver.models.input.PurchaseVehicleInput
import br.com.fiap.automotivesaleshub.core.application.ports.driver.models.input.RegisterVehicleInput
import br.com.fiap.automotivesaleshub.core.application.ports.driver.models.input.UpdateVehicleInput
import br.com.fiap.automotivesaleshub.core.application.ports.driver.models.output.PurchaseVehicleOutput
import br.com.fiap.automotivesaleshub.core.application.ports.driver.models.output.RegisterVehicleOutput
import br.com.fiap.automotivesaleshub.core.application.ports.driver.models.output.UpdateVehicleOutput
import jakarta.transaction.Transactional
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/vehicles")
class VehicleController(
    private val registerVehicleDriverPort: RegisterVehicleDriverPort,
    private val updateVehicleDriverPort: UpdateVehicleDriverPort,
    private val purchaseVehicleDriverPort: PurchaseVehicleDriverPort,
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    fun registerVehicle(
        @Validated @RequestBody input: RegisterVehicleInput
    ): RegisterVehicleOutput {
        return registerVehicleDriverPort.execute(input)
    }

    @PutMapping("/{vehicleId}")
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    fun updateVehicle(
        @PathVariable(name = "vehicleId", required = true) vehicleId: String,
        @Validated @RequestBody input: UpdateVehicleInput,
    ): UpdateVehicleOutput {
        // TODO: get ID from path variable only
        return updateVehicleDriverPort.execute(input)
    }

    @PostMapping("/purchase")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Transactional
    fun purchaseVehicle(
        @Validated @RequestBody input: PurchaseVehicleInput
    ): PurchaseVehicleOutput {
        return purchaseVehicleDriverPort.execute(input)
    }
}
