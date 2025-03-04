package co.com.pragma.r2dbc.services;

import co.com.pragma.model.franchise.models.Branch;
import co.com.pragma.model.franchise.spi.IBranchPersistencePort;
import co.com.pragma.r2dbc.mappers.IBranchEntityMapper;
import co.com.pragma.r2dbc.repositories.IBranchRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class BranchPersistenceAdapter implements IBranchPersistencePort {

    private final IBranchRepository branchRepository;
    private final IBranchEntityMapper branchEntityMapper;

    public BranchPersistenceAdapter(IBranchRepository branchRepository, IBranchEntityMapper branchEntityMapper) {
        this.branchRepository = branchRepository;
        this.branchEntityMapper = branchEntityMapper;
    }

    @Override
    public Mono<Branch> save(Branch branch) {
        return branchRepository.save(branchEntityMapper.toBranchEntity(branch))
                .map(branchEntityMapper::toBranchModel);
    }

    @Override
    public Mono<Branch> findById(Long id) {
        return branchRepository.findById(id)
                .map(branchEntityMapper::toBranchModel);
    }

    @Override
    public Mono<Branch> findByName(String name) {
        return branchRepository.findByName(name)
                .map(branchEntityMapper::toBranchModel);
    }

    @Override
    public Flux<Branch> findBranchesByFranchiseId(Long franchiseId) {
        return branchRepository.findByFranchiseId(franchiseId)
                .map(branchEntityMapper::toBranchModel);
    }
}
