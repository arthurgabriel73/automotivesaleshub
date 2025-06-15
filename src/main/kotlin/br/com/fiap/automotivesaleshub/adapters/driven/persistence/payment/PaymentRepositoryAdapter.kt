package br.com.fiap.automotivesaleshub.adapters.driven.persistence.payment

import br.com.fiap.automotivesaleshub.core.application.ports.driven.PaymentRepository
import br.com.fiap.automotivesaleshub.core.domain.payment.models.Payment
import jakarta.inject.Named
import java.util.*

@Named
class PaymentRepositoryAdapter(private val jpaRepository: JpaPaymentRepository) :
    PaymentRepository {
    override fun create(payment: Payment): Payment {
        val entity = PaymentEntity.fromDomain(payment)
        return jpaRepository.save(entity).toDomain()
    }

    override fun findByOrder(order: UUID): Payment? {
        return jpaRepository.findByOrder(order)?.toDomain()
    }

    override fun update(payment: Payment) {
        jpaRepository.save(PaymentEntity.fromDomain(payment))
    }
}
