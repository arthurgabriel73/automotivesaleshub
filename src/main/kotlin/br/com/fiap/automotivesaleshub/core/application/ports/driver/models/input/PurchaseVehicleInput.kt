package br.com.fiap.automotivesaleshub.core.application.ports.driver.models.input

data class PurchaseVehicleInput(val orderId: String, val vehicleId: String) {

    init {
        require(orderId.isNotBlank()) { "Order ID cannot be blank" }
        require(vehicleId.isNotBlank()) { "Vehicle ID cannot be blank" }
    }
}
