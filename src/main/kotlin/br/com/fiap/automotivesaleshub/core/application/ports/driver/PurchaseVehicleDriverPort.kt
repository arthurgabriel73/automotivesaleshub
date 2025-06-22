package br.com.fiap.automotivesaleshub.core.application.ports.driver

import br.com.fiap.automotivesaleshub.core.application.ports.driver.models.input.PurchaseVehicleInput
import br.com.fiap.automotivesaleshub.core.application.ports.driver.models.output.PurchaseVehicleOutput

interface PurchaseVehicleDriverPort {
    fun execute(input: PurchaseVehicleInput): PurchaseVehicleOutput
}
