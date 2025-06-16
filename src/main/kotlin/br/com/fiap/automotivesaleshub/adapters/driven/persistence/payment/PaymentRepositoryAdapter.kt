package br.com.fiap.automotivesaleshub.adapters.driven.persistence.payment

import br.com.fiap.automotivesaleshub.core.application.ports.driven.PaymentRepository
import br.com.fiap.automotivesaleshub.core.domain.payment.models.Payment
import br.com.fiap.automotivesaleshub.core.domain.payment.valueObjects.OrderId
import jakarta.inject.Named

@Named
class PaymentRepositoryAdapter(private val jpaRepository: JpaPaymentRepository) :
    PaymentRepository {
    override fun create(payment: Payment): Payment {
        val entity = PaymentEntity.fromDomain(payment)
        return jpaRepository.save(entity).toDomain()
    }

    override fun findByOrderId(orderId: OrderId): Payment? {
        return jpaRepository.findByOrderId(orderId.id)?.toDomain()
    }

    override fun update(payment: Payment) {
        jpaRepository.save(PaymentEntity.fromDomain(payment))
    }
}
