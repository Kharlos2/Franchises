package co.com.pragma.config;

import co.com.pragma.model.franchise.api.IBranchServicePort;
import co.com.pragma.model.franchise.api.IFranchiseServicePort;
import co.com.pragma.model.franchise.spi.IBranchPersistencePort;
import co.com.pragma.model.franchise.spi.IFranchiseBranchPersistencePort;
import co.com.pragma.model.franchise.spi.IFranchisePersistencePort;
import co.com.pragma.r2dbc.mappers.IBranchEntityMapper;
import co.com.pragma.r2dbc.mappers.IFranchiseBranchEntityMapper;
import co.com.pragma.r2dbc.mappers.IFranchiseEntityMapper;
import co.com.pragma.r2dbc.repositories.IBranchRepository;
import co.com.pragma.r2dbc.repositories.IFranchiseBranchRepository;
import co.com.pragma.r2dbc.repositories.IFranchiseRepository;
import co.com.pragma.r2dbc.services.BranchPersistenceAdapter;
import co.com.pragma.r2dbc.services.FranchiseBranchPersistenceAdapter;
import co.com.pragma.r2dbc.services.FranchisePersistenceAdapter;
import co.com.pragma.usecase.franchise.BranchUseCase;
import co.com.pragma.usecase.franchise.FranchiseUseCase;
import org.springframework.context.annotation.Bean;

public class UseCasesConfig {

        private final IFranchiseRepository franchiseRepository;
        private final IFranchiseEntityMapper franchiseEntityMapper;
        private final IBranchRepository branchRepository;
        private final IBranchEntityMapper branchEntityMapper;
        private final IFranchiseBranchRepository franchiseBranchRepository;
        private final IFranchiseBranchEntityMapper franchiseBranchEntityMapper;


        public UseCasesConfig(IFranchiseRepository franchiseRepository, IFranchiseEntityMapper franchiseEntityMapper, IBranchRepository branchRepository, IBranchEntityMapper branchEntityMapper, IFranchiseBranchRepository franchiseBranchRepository, IFranchiseBranchEntityMapper franchiseBranchEntityMapper) {
                this.franchiseRepository = franchiseRepository;
                this.franchiseEntityMapper = franchiseEntityMapper;
            this.branchRepository = branchRepository;
            this.branchEntityMapper = branchEntityMapper;
            this.franchiseBranchRepository = franchiseBranchRepository;
            this.franchiseBranchEntityMapper = franchiseBranchEntityMapper;
        }

        @Bean
        public IFranchisePersistencePort franchisePersistencePort(){
                return new FranchisePersistenceAdapter(franchiseRepository, franchiseEntityMapper);
        }

        @Bean
        public IFranchiseServicePort iFranchiseServicePort( IFranchisePersistencePort franchisePersistencePort ){
                return new FranchiseUseCase(franchisePersistencePort);
        }

        @Bean
        public IBranchPersistencePort iBranchPersistencePort () {
                return new BranchPersistenceAdapter(branchRepository, branchEntityMapper);
        }

        @Bean
        public IFranchiseBranchPersistencePort franchiseBranchPersistencePort() {
                return new FranchiseBranchPersistenceAdapter(franchiseBranchRepository,franchiseBranchEntityMapper);
        }

        @Bean
        public IBranchServicePort branchServicePort (
                IBranchPersistencePort branchPersistencePort,
                IFranchiseBranchPersistencePort franchiseBranchPersistencePort,
                IFranchisePersistencePort franchisePersistencePort
        ){
                return new BranchUseCase(branchPersistencePort,franchiseBranchPersistencePort,franchisePersistencePort);
        }

}
