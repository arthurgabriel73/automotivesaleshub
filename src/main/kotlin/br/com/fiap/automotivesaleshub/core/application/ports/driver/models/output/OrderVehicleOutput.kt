package br.com.fiap.automotivesaleshub.core.application.ports.driver.models.output

data class OrderVehicleOutput(val result: Boolean, val message: String) {
    companion object {
        fun success(): OrderVehicleOutput = OrderVehicleOutput(true, "Vehicle ordered successfully")

        fun failure(message: String): OrderVehicleOutput = OrderVehicleOutput(false, message)
    }
}
