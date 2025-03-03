package co.com.pragma.r2dbc.services;

import co.com.pragma.model.franchise.models.BranchProduct;
import co.com.pragma.model.franchise.spi.IBranchProductPersistencePort;
import co.com.pragma.r2dbc.mappers.IBranchProductEntityMapper;
import co.com.pragma.r2dbc.repositories.IBranchProductRepository;
import reactor.core.publisher.Mono;

public class BranchProductPersistenceAdapter implements IBranchProductPersistencePort {

    private final IBranchProductRepository branchProductRepository;
    private final IBranchProductEntityMapper branchProductEntityMapper;

    public BranchProductPersistenceAdapter(IBranchProductRepository branchProductRepository, IBranchProductEntityMapper branchProductEntityMapper) {
        this.branchProductRepository = branchProductRepository;
        this.branchProductEntityMapper = branchProductEntityMapper;
    }

    @Override
    public Mono<Void> save(BranchProduct branchProduct) {
        return branchProductRepository.save(branchProductEntityMapper.toEntity(branchProduct)).then();
    }

}
