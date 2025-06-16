package br.com.fiap.automotivesaleshub.adapters.driven.persistence.payment

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface JpaPaymentRepository : JpaRepository<PaymentEntity, UUID> {
    fun findByOrderId(orderId: UUID): PaymentEntity?
}
