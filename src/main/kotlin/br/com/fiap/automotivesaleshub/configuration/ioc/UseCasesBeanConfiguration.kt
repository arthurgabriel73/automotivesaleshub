package br.com.fiap.automotivesaleshub.configuration.ioc

import br.com.fiap.automotivesaleshub.adapters.driver.webhook.UpdatePaymentRequestAdapter
import br.com.fiap.automotivesaleshub.adapters.driver.webhook.adapters.MercadoPagoAdapter
import br.com.fiap.automotivesaleshub.core.application.ports.driven.PaymentRepository
import br.com.fiap.automotivesaleshub.core.application.ports.driven.VehicleRepository
import br.com.fiap.automotivesaleshub.core.application.ports.driven.VehicleSalesService
import br.com.fiap.automotivesaleshub.core.application.ports.driver.RegisterVehicleDriverPort
import br.com.fiap.automotivesaleshub.core.application.ports.driver.UpdateVehicleDriverPort
import br.com.fiap.automotivesaleshub.core.application.ports.payment.driver.UpdatePaymentDriverPort
import br.com.fiap.automotivesaleshub.core.application.useCases.RegisterVehicleUseCase
import br.com.fiap.automotivesaleshub.core.application.useCases.UpdateVehicleUseCase
import br.com.fiap.automotivesaleshub.core.application.useCases.payment.UpdatePaymentUseCase
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UseCasesBeanConfiguration {
    @Bean
    fun registerVehicleUseCase(
        vehicleRepository: VehicleRepository,
        vehicleSalesService: VehicleSalesService,
    ): RegisterVehicleDriverPort {
        return RegisterVehicleUseCase(vehicleRepository, vehicleSalesService)
    }

    @Bean
    fun updateVehicleUseCase(
        vehicleRepository: VehicleRepository,
        vehicleSalesService: VehicleSalesService,
    ): UpdateVehicleDriverPort {
        return UpdateVehicleUseCase(vehicleRepository, vehicleSalesService)
    }

    @Bean
    fun updatePaymentUseCase(
        paymentRepository: PaymentRepository,
        vehicleRepository: VehicleRepository,
        vehicleSalesService: VehicleSalesService,
    ): UpdatePaymentDriverPort {
        return UpdatePaymentUseCase(paymentRepository, vehicleRepository, vehicleSalesService)
    }

    @Bean
    fun updatePaymentRequestAdapter(): UpdatePaymentRequestAdapter {
        return MercadoPagoAdapter()
    }
}
