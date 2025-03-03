package co.com.pragma.r2dbc.services;

import co.com.pragma.model.franchise.models.Branch;
import co.com.pragma.model.franchise.spi.IBranchPersistencePort;
import co.com.pragma.r2dbc.mappers.IBranchEntityMapper;
import co.com.pragma.r2dbc.repositories.IBranchRepository;
import reactor.core.publisher.Mono;

public class BranchPersistenceAdapter implements IBranchPersistencePort {

    private final IBranchRepository branchRepository;
    private final IBranchEntityMapper brandEntityMapper;

    public BranchPersistenceAdapter(IBranchRepository branchRepository, IBranchEntityMapper brandEntityMapper) {
        this.branchRepository = branchRepository;
        this.brandEntityMapper = brandEntityMapper;
    }

    @Override
    public Mono<Branch> save(Branch branch) {
        return branchRepository.save(brandEntityMapper.toBranchEntity(branch))
                .map(brandEntityMapper::toBranchModel);
    }

    @Override
    public Mono<Branch> findById(Long id) {
        return branchRepository.findById(id)
                .map(brandEntityMapper::toBranchModel);
    }

    @Override
    public Mono<Branch> findByName(String name) {
        return branchRepository.findByName(name)
                .map(brandEntityMapper::toBranchModel);
    }
}
