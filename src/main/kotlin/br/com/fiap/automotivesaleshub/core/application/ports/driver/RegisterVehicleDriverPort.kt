package br.com.fiap.automotivesaleshub.core.application.ports.driver

import br.com.fiap.automotivesaleshub.core.application.ports.driver.models.input.RegisterVehicleInput
import br.com.fiap.automotivesaleshub.core.application.ports.driver.models.output.RegisterVehicleOutput

interface RegisterVehicleDriverPort {
    fun execute(input: RegisterVehicleInput): RegisterVehicleOutput
}
