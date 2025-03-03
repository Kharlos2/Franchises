package co.com.pragma.config;

import co.com.pragma.model.franchise.api.IFranchiseServicePort;
import co.com.pragma.model.franchise.spi.IFranchisePersistencePort;
import co.com.pragma.r2dbc.mappers.IFranchiseEntityMapper;
import co.com.pragma.r2dbc.repositories.IFranchiseRepository;
import co.com.pragma.r2dbc.services.FranchisePersistenceAdapter;
import co.com.pragma.usecase.franchise.FranchiseUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

public class UseCasesConfig {

        private final IFranchiseRepository franchiseRepository;
        private final IFranchiseEntityMapper franchiseEntityMapper;

        public UseCasesConfig(IFranchiseRepository franchiseRepository, IFranchiseEntityMapper franchiseEntityMapper) {
                this.franchiseRepository = franchiseRepository;
                this.franchiseEntityMapper = franchiseEntityMapper;
        }

        @Bean
        public IFranchisePersistencePort franchisePersistencePort(){
                return new FranchisePersistenceAdapter(franchiseRepository, franchiseEntityMapper);
        }

        @Bean
        public IFranchiseServicePort iFranchiseServicePort( IFranchisePersistencePort franchisePersistencePort ){
                return new FranchiseUseCase(franchisePersistencePort);
        }

}
