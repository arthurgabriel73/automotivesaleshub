package br.com.fiap.automotivesaleshub.core.application.useCases.payment

import br.com.fiap.automotivesaleshub.adapters.driven.api.InMemoryVehicleSalesService
import br.com.fiap.automotivesaleshub.adapters.driven.persistence.InMemoryVehicleRepository
import br.com.fiap.automotivesaleshub.adapters.driven.persistence.payment.InMemoryPaymentRepository
import br.com.fiap.automotivesaleshub.core.application.ports.driven.PaymentRepository
import br.com.fiap.automotivesaleshub.core.application.ports.payment.driver.models.input.UpdatePaymentInput
import br.com.fiap.automotivesaleshub.core.application.useCases.exceptions.PaymentNotFoundException
import br.com.fiap.automotivesaleshub.core.domain.payment.models.Payment
import br.com.fiap.automotivesaleshub.core.domain.payment.valueObjects.OrderId
import br.com.fiap.automotivesaleshub.core.domain.payment.valueObjects.PaymentId
import br.com.fiap.automotivesaleshub.core.domain.payment.valueObjects.PaymentStatus
import br.com.fiap.automotivesaleshub.core.domain.vehicle.models.Vehicle
import br.com.fiap.automotivesaleshub.core.domain.vehicle.valueObjects.*
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import java.time.Instant
import java.util.*
import kotlin.test.Test

class UpdatePaymentUseCaseTest {
    private lateinit var paymentRepository: PaymentRepository
    private val vehicleRepository = InMemoryVehicleRepository()
    private val vehicleSalesService = InMemoryVehicleSalesService()
    private lateinit var sut: UpdatePaymentUseCase

    @BeforeEach
    fun setUp() {
        paymentRepository = InMemoryPaymentRepository()
        sut = UpdatePaymentUseCase(paymentRepository, vehicleRepository, vehicleSalesService)
    }

    val existingOrder = OrderId(UUID.randomUUID())
    val existingPaymentId = PaymentId(UUID.randomUUID())
    val existingVehicleId = VehicleId(UUID.randomUUID())
    val existingPayment =
        Payment(
            paymentId = existingPaymentId,
            status = PaymentStatus.PENDING,
            orderId = existingOrder,
            vehicleId = existingVehicleId,
            createdAt = Instant.now(),
        )
    val existingVehicle =
        Vehicle(
            vehicleId = existingVehicleId,
            specifications =
                Specifications(
                    make = "Fiat",
                    model = "Uno",
                    version = "Way",
                    yearFabrication = "2020",
                    yearModel = "2021",
                    kilometers = 15000,
                    color = "Red",
                ),
            plate = Plate(plate = "ABC-1234"),
            price = Price(amount = 30000L, currency = PriceCurrency.BRL),
            status = VehicleStatus.PROCESSING,
            createdAt = Instant.now(),
        )

    val updatePaymentInput: UpdatePaymentInput =
        UpdatePaymentInput(existingOrder.string(), PaymentStatus.APPROVED.name)

    @Test
    fun `should update a payment successfully`() {
        // Arrange
        paymentRepository.create(existingPayment)
        vehicleRepository.create(existingVehicle)

        // Act
        sut.execute(updatePaymentInput)

        // Assert
        val payment = paymentRepository.findByOrderId(existingPayment.orderId)
        if (payment == null) throw AssertionError("Updated payment should not be null")
        payment shouldNotBe null
        payment.paymentId shouldBe existingPayment.paymentId
        payment.status shouldBe PaymentStatus.APPROVED
        payment.orderId shouldBe existingOrder
        payment.createdAt shouldBe existingPayment.createdAt
        payment.updatedAt shouldNotBe null
        payment.updatedAt!!.isAfter(existingPayment.createdAt)
    }

    @Test
    fun `should handle rejected payment and update vehicle status to AVAILABLE`() {
        // Arrange
        val rejectedPaymentInput =
            UpdatePaymentInput(existingOrder.string(), PaymentStatus.REJECTED.name)
        paymentRepository.create(existingPayment)
        vehicleRepository.create(existingVehicle)

        // Act
        sut.execute(rejectedPaymentInput)

        // Assert
        val payment = paymentRepository.findByOrderId(existingPayment.orderId)
        if (payment == null) throw AssertionError("Updated payment should not be null")
        payment.status shouldBe PaymentStatus.REJECTED

        val vehicle = vehicleRepository.findById(existingVehicleId)
        if (vehicle == null) throw AssertionError("Vehicle should not be null")
        vehicle.status shouldBe VehicleStatus.AVAILABLE
    }

    @Test
    fun `should throw exception when payment not found for orderId`() {
        // Arrange
        val nonExistentOrderId = OrderId(UUID.randomUUID())
        val updateInput =
            UpdatePaymentInput(nonExistentOrderId.string(), PaymentStatus.APPROVED.name)

        // Act & Assert
        assertThrows<PaymentNotFoundException> { sut.execute(updateInput) }
    }
}
