package br.com.fiap.automotivesaleshub.core.application.useCases.payment

import br.com.fiap.automotivesaleshub.adapters.driven.persistence.payment.InMemoryPaymentRepository
import br.com.fiap.automotivesaleshub.core.application.ports.driven.PaymentRepository
import br.com.fiap.automotivesaleshub.core.application.ports.payment.driver.models.input.UpdatePaymentInput
import br.com.fiap.automotivesaleshub.core.domain.payment.models.Payment
import br.com.fiap.automotivesaleshub.core.domain.payment.valueObjects.PaymentId
import br.com.fiap.automotivesaleshub.core.domain.payment.valueObjects.PaymentStatus
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.BeforeEach
import java.time.Instant
import java.util.*
import kotlin.test.Test

class UpdatePaymentUseCaseTest {
    private lateinit var paymentRepository: PaymentRepository
    private lateinit var sut: UpdatePaymentUseCase

    @BeforeEach
    fun setUp() {
        paymentRepository = InMemoryPaymentRepository()
        sut = UpdatePaymentUseCase(paymentRepository)
    }

    val existingOrder: UUID = UUID.randomUUID()
    val existingPaymentId = PaymentId(UUID.randomUUID())
    val existingPayment =
        Payment(
            paymentId = existingPaymentId,
            status = PaymentStatus.PENDING,
            order = existingOrder,
            createdAt = Instant.now(),
        )

    val updatePaymentInput: UpdatePaymentInput =
        UpdatePaymentInput(existingOrder.toString(), PaymentStatus.APPROVED)

    @Test
    fun `should update a payment successfully`() {
        // Arrange
        paymentRepository.create(existingPayment)

        // Act
        sut.execute(updatePaymentInput)

        // Assert
        val payment = paymentRepository.findByOrder(existingPayment.order)
        if (payment == null) throw AssertionError("Updated payment should not be null")
        payment shouldNotBe null
        payment.paymentId shouldBe existingPayment.paymentId
        payment.status shouldBe PaymentStatus.APPROVED
        payment.order shouldBe existingOrder
        payment.createdAt shouldBe existingPayment.createdAt
        payment.updatedAt shouldNotBe null
        payment.updatedAt!!.isAfter(existingPayment.createdAt)
    }
}
