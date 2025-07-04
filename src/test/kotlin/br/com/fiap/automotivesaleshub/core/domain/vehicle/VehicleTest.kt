package br.com.fiap.automotivesaleshub.core.domain.vehicle

import br.com.fiap.automotivesaleshub.core.domain.vehicle.exceptions.InvalidVehicleStatusException
import br.com.fiap.automotivesaleshub.core.domain.vehicle.models.Vehicle
import br.com.fiap.automotivesaleshub.core.domain.vehicle.valueObjects.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.time.Instant
import java.util.*
import java.util.stream.Stream
import kotlin.test.Test

class VehicleTest {

    val vehicleId = VehicleId(UUID.randomUUID())
    val specifications =
        Specifications(
            "Make",
            "Model",
            "Version",
            yearFabrication = "2025",
            yearModel = "2026",
            kilometers = 0,
            color = "Black",
        )
    val plate = Plate("ABC-1234")
    val price = Price(50000, PriceCurrency.BRL)
    val status = VehicleStatus.AVAILABLE
    val createdAt: Instant = Instant.now()

    @Test
    fun `should create vehicle with correct fields`() {
        // Arrange & Act
        val vehicle = Vehicle(vehicleId, specifications, plate, price, status, createdAt)

        // Assert
        assertEquals(vehicleId, vehicle.vehicleId)
        assertEquals(specifications, vehicle.specifications)
        assertEquals(plate, vehicle.plate)
        assertEquals(price, vehicle.price)
        assertEquals(status, vehicle.status)
        assertEquals(createdAt, vehicle.createdAt)
    }

    @Test
    fun `should update specifications correctly and set updatedAt`() {
        // Arrange
        val vehicle = Vehicle(vehicleId, specifications, plate, price, status, createdAt)
        val newSpecifications =
            Specifications(
                "New Make",
                "New Model",
                "New Version",
                yearFabrication = "2025",
                yearModel = "2026",
                kilometers = 1000,
                color = "Red",
            )
        val beforeUpdate = Instant.now()

        // Act
        val updatedVehicle = vehicle.updateSpecifications(newSpecifications)

        // Assert
        assertEquals(newSpecifications, updatedVehicle.specifications)
        assertNotNull(updatedVehicle.updatedAt)
        assertTrue(updatedVehicle.updatedAt!!.isAfter(beforeUpdate))
    }

    @Test
    fun `should update plate correctly and set updatedAt`() {
        // Arrange
        val vehicle = Vehicle(vehicleId, specifications, plate, price, status, createdAt)
        val newPlate = Plate("XYZ-5678")
        val beforeUpdate = Instant.now()

        // Act
        val updatedVehicle = vehicle.updatePlate(newPlate)

        // Assert
        assertEquals(newPlate, updatedVehicle.plate)
        assertNotNull(updatedVehicle.updatedAt)
        assertTrue(updatedVehicle.updatedAt!!.isAfter(beforeUpdate))
    }

    @Test
    fun `should update price correctly and set updatedAt`() {
        // Arrange
        val vehicle = Vehicle(vehicleId, specifications, plate, price, status, createdAt)
        val newPrice = Price(60000, PriceCurrency.BRL)
        val beforeUpdate = Instant.now()

        // Act
        val updatedVehicle = vehicle.updatePrice(newPrice)

        // Assert
        assertEquals(newPrice, updatedVehicle.price)
        assertNotNull(updatedVehicle.updatedAt)
        assertTrue(updatedVehicle.updatedAt!!.isAfter(beforeUpdate))
    }

    @Test
    fun `should allow a valid status transition and set updatedAt`() {
        // Arrange
        val vehicle = Vehicle(vehicleId, specifications, plate, price, status, createdAt)
        val newStatus = VehicleStatus.PROCESSING
        val beforeUpdate = Instant.now()

        // Act
        val updatedVehicle = vehicle.updateStatus(newStatus)

        // Assert
        assertEquals(newStatus, updatedVehicle.status)
        assertNotNull(updatedVehicle.updatedAt)
        assertTrue(updatedVehicle.updatedAt!!.isAfter(beforeUpdate))
    }

    @Test
    fun `should throw exception for invalid status transition`() {
        // Arrange
        val vehicle = Vehicle(vehicleId, specifications, plate, price, status, createdAt)
        val invalidStatus = VehicleStatus.SOLD

        // Act & Assert
        assertThrows<InvalidVehicleStatusException> { vehicle.updateStatus(invalidStatus) }
    }

    @ParameterizedTest(name = "From {0} to {1} should be {2}")
    @MethodSource("statusTransitions")
    fun `should handle status transitions correctly`(
        currentStatus: VehicleStatus,
        newStatus: VehicleStatus,
        expectedSuccess: Boolean,
    ) {
        // Arrange
        val vehicle = Vehicle(vehicleId, specifications, plate, price, currentStatus, createdAt)

        // Act & Assert
        if (expectedSuccess) {
            val updatedVehicle = vehicle.updateStatus(newStatus)
            assertEquals(newStatus, updatedVehicle.status)
        } else {
            assertThrows<InvalidVehicleStatusException> { vehicle.updateStatus(newStatus) }
        }
    }

    companion object {
        @JvmStatic
        fun statusTransitions(): Stream<Arguments> {
            return Stream.of(
                // Valid transitions
                Arguments.of(VehicleStatus.AVAILABLE, VehicleStatus.PROCESSING, true),
                Arguments.of(VehicleStatus.AVAILABLE, VehicleStatus.UNAVAILABLE, true),
                Arguments.of(VehicleStatus.PROCESSING, VehicleStatus.AVAILABLE, true),
                Arguments.of(VehicleStatus.PROCESSING, VehicleStatus.UNAVAILABLE, true),
                Arguments.of(VehicleStatus.PROCESSING, VehicleStatus.SOLD, true),
                Arguments.of(VehicleStatus.UNAVAILABLE, VehicleStatus.AVAILABLE, true),

                // Invalid transitions
                Arguments.of(VehicleStatus.AVAILABLE, VehicleStatus.AVAILABLE, false),
                Arguments.of(VehicleStatus.AVAILABLE, VehicleStatus.SOLD, false),
                Arguments.of(VehicleStatus.PROCESSING, VehicleStatus.PROCESSING, false),
                Arguments.of(VehicleStatus.SOLD, VehicleStatus.AVAILABLE, false),
                Arguments.of(VehicleStatus.SOLD, VehicleStatus.PROCESSING, false),
                Arguments.of(VehicleStatus.SOLD, VehicleStatus.SOLD, false),
                Arguments.of(VehicleStatus.SOLD, VehicleStatus.UNAVAILABLE, false),
                Arguments.of(VehicleStatus.UNAVAILABLE, VehicleStatus.PROCESSING, false),
                Arguments.of(VehicleStatus.UNAVAILABLE, VehicleStatus.SOLD, false),
                Arguments.of(VehicleStatus.UNAVAILABLE, VehicleStatus.UNAVAILABLE, false),
            )
        }
    }
}
