package br.com.fiap.automotivesaleshub.core.application.ports.driver

import br.com.fiap.automotivesaleshub.core.application.ports.driver.models.input.OrderVehicleInput
import br.com.fiap.automotivesaleshub.core.application.ports.driver.models.output.OrderVehicleOutput

interface OrderVehicleDriverPort {
    fun execute(input: OrderVehicleInput): OrderVehicleOutput
}
