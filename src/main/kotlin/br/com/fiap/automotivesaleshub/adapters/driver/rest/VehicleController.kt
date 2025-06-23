package br.com.fiap.automotivesaleshub.adapters.driver.rest

import br.com.fiap.automotivesaleshub.adapters.driver.rest.dto.PurchaseVehicleRequest
import br.com.fiap.automotivesaleshub.adapters.driver.rest.dto.RegisterVehicleRequest
import br.com.fiap.automotivesaleshub.adapters.driver.rest.dto.UpdateVehicleRequest
import br.com.fiap.automotivesaleshub.core.application.ports.driver.PurchaseVehicleDriverPort
import br.com.fiap.automotivesaleshub.core.application.ports.driver.RegisterVehicleDriverPort
import br.com.fiap.automotivesaleshub.core.application.ports.driver.UpdateVehicleDriverPort
import br.com.fiap.automotivesaleshub.core.application.ports.driver.models.output.PurchaseVehicleOutput
import br.com.fiap.automotivesaleshub.core.application.ports.driver.models.output.RegisterVehicleOutput
import br.com.fiap.automotivesaleshub.core.application.ports.driver.models.output.UpdateVehicleOutput
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
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
        @Valid @RequestBody request: RegisterVehicleRequest
    ): RegisterVehicleOutput {
        return registerVehicleDriverPort.execute(request.toInput())
    }

    @PutMapping("/{vehicleId}")
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    fun updateVehicle(
        @PathVariable(name = "vehicleId", required = true) vehicleId: String,
        @Valid @RequestBody request: UpdateVehicleRequest,
    ): UpdateVehicleOutput {
        // TODO: get ID from path variable only
        return updateVehicleDriverPort.execute(request.toInput())
    }

    @PostMapping("/purchase")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Transactional
    fun purchaseVehicle(
        @Valid @RequestBody request: PurchaseVehicleRequest
    ): PurchaseVehicleOutput {
        return purchaseVehicleDriverPort.execute(request.toInput())
    }
}
