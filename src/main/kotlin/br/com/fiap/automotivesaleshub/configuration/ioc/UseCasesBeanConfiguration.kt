package br.com.fiap.automotivesaleshub.configuration.ioc

import br.com.fiap.automotivesaleshub.adapters.driven.persistence.InMemoryVehicleRepository
import br.com.fiap.automotivesaleshub.core.application.ports.driven.VehicleRepository
import br.com.fiap.automotivesaleshub.core.application.ports.driver.models.RegisterVehicleDriverPort
import br.com.fiap.automotivesaleshub.core.application.useCases.RegisterVehicleUseCase
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UseCasesBeanConfiguration {
    @Bean
    fun registerVehicleUseCase(): RegisterVehicleDriverPort {
        val vehicleRepository: VehicleRepository = InMemoryVehicleRepository()
        return RegisterVehicleUseCase(vehicleRepository)
    }
}
