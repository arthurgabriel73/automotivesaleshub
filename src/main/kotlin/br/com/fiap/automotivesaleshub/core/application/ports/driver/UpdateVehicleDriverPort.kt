package br.com.fiap.automotivesaleshub.core.application.ports.driver

import br.com.fiap.automotivesaleshub.core.application.ports.driver.models.input.UpdateVehicleInput
import br.com.fiap.automotivesaleshub.core.application.ports.driver.models.output.UpdateVehicleOutput

interface UpdateVehicleDriverPort {
    fun execute(input: UpdateVehicleInput): UpdateVehicleOutput
}
