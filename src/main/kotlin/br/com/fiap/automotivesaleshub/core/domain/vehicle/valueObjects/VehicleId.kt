package br.com.fiap.automotivesaleshub.core.domain.vehicle.valueObjects

import java.util.*

@JvmInline
value class VehicleId(val id: UUID) {
    fun string() = id.toString()
}
