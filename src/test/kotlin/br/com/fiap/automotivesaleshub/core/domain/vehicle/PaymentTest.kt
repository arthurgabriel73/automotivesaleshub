package br.com.fiap.automotivesaleshub.core.domain.vehicle

import br.com.fiap.automotivesaleshub.core.domain.payment.exceptions.InvalidPaymentStatusException
import br.com.fiap.automotivesaleshub.core.domain.payment.models.Payment
import br.com.fiap.automotivesaleshub.core.domain.payment.valueObjects.PaymentId
import br.com.fiap.automotivesaleshub.core.domain.payment.valueObjects.PaymentStatus
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.comparables.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import java.time.Instant
import java.util.*
import kotlin.test.Test

class PaymentTest {

    val paymentId = PaymentId(UUID.randomUUID())
    val order: UUID = UUID.randomUUID()
    val createdAt: Instant = Instant.now()

    @Test
    fun `should create a payment with correct fields`() {
        // Arrange & Act
        val payment = Payment(paymentId, PaymentStatus.PENDING, order, createdAt)

        // Assert
        payment shouldNotBe null
        payment.paymentId shouldBe paymentId
        payment.status shouldBe PaymentStatus.PENDING
        payment.order shouldBe order
        payment.createdAt shouldBe createdAt
    }

    @Test
    fun `should update status correctly and set updatedAt`() {
        // Arrange
        val payment = Payment(paymentId, PaymentStatus.PENDING, order, createdAt)

        // Act
        val updatedPayment = payment.updateStatus(PaymentStatus.APPROVED)

        // Assert
        updatedPayment shouldNotBe null
        updatedPayment.status shouldBe PaymentStatus.APPROVED
        updatedPayment.updatedAt?.shouldBeGreaterThan(createdAt)
    }

    @Test
    fun `should throw exception for invalid status transition`() {
        // Arrange
        val payment = Payment(paymentId, PaymentStatus.PENDING, order, createdAt)
        payment.updateStatus(PaymentStatus.APPROVED)

        // Act & Assert
        shouldThrow<InvalidPaymentStatusException> { payment.updateStatus(PaymentStatus.PENDING) }
    }
}
