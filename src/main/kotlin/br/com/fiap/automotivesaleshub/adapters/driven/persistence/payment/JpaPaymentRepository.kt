package br.com.fiap.automotivesaleshub.adapters.driven.persistence.payment

import java.util.*
import org.springframework.data.jpa.repository.JpaRepository

interface JpaPaymentRepository : JpaRepository<PaymentEntity, UUID> {
    fun findByOrder(order: UUID): PaymentEntity?
}
