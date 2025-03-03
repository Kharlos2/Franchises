package co.com.pragma.config;

import co.com.pragma.model.franchise.api.IBranchServicePort;
import co.com.pragma.model.franchise.api.IFranchiseServicePort;
import co.com.pragma.model.franchise.api.IProductServicePort;
import co.com.pragma.model.franchise.spi.*;
import co.com.pragma.r2dbc.mappers.*;
import co.com.pragma.r2dbc.repositories.*;
import co.com.pragma.r2dbc.services.*;
import co.com.pragma.usecase.franchise.BranchUseCase;
import co.com.pragma.usecase.franchise.FranchiseUseCase;
import co.com.pragma.usecase.franchise.ProductUseCase;
import org.springframework.context.annotation.Bean;


public class UseCasesConfig {

        private final IFranchiseRepository franchiseRepository;
        private final IFranchiseEntityMapper franchiseEntityMapper;
        private final IBranchRepository branchRepository;
        private final IBranchEntityMapper branchEntityMapper;
        private final IFranchiseBranchRepository franchiseBranchRepository;
        private final IFranchiseBranchEntityMapper franchiseBranchEntityMapper;
        private final IBranchProductRepository branchProductRepository;
        private final IBranchProductEntityMapper branchProductEntityMapper;
        private final IProductRepository productRepository;
        private final IProductEntityMapper productEntityMapper;

    public UseCasesConfig(IFranchiseRepository franchiseRepository, IFranchiseEntityMapper franchiseEntityMapper, IBranchRepository branchRepository, IBranchEntityMapper branchEntityMapper, IFranchiseBranchRepository franchiseBranchRepository, IFranchiseBranchEntityMapper franchiseBranchEntityMapper, IBranchProductRepository branchProductRepository, IBranchProductEntityMapper branchProductEntityMapper, IProductRepository productRepository, IProductEntityMapper productEntityMapper) {
        this.franchiseRepository = franchiseRepository;
        this.franchiseEntityMapper = franchiseEntityMapper;
        this.branchRepository = branchRepository;
        this.branchEntityMapper = branchEntityMapper;
        this.franchiseBranchRepository = franchiseBranchRepository;
        this.franchiseBranchEntityMapper = franchiseBranchEntityMapper;
        this.branchProductRepository = branchProductRepository;
        this.branchProductEntityMapper = branchProductEntityMapper;
        this.productRepository = productRepository;
        this.productEntityMapper = productEntityMapper;
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

        @Bean
        public IBranchProductPersistencePort branchProductPersistencePort(){
            return new BranchProductPersistenceAdapter(branchProductRepository, branchProductEntityMapper);
        }

        @Bean
        public IProductPersistencePort productPersistencePort(){
            return new ProductPersistenceAdapter(productRepository, productEntityMapper);
        }

        @Bean
        public IProductServicePort productServicePort(
                IProductPersistencePort productPersistencePort,
                IBranchPersistencePort iBranchPersistencePort,
                IBranchProductPersistencePort branchProductPersistencePort
        ){
            return new ProductUseCase(productPersistencePort,iBranchPersistencePort,branchProductPersistencePort);
        }
}

