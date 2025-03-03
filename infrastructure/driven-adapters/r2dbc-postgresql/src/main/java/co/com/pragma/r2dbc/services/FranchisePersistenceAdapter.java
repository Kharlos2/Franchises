package co.com.pragma.r2dbc.services;

import co.com.pragma.model.franchise.models.Franchise;
import co.com.pragma.model.franchise.spi.IFranchisePersistencePort;
import co.com.pragma.r2dbc.mappers.IFranchiseEntityMapper;
import co.com.pragma.r2dbc.repositories.IFranchiseRepository;
import reactor.core.publisher.Mono;

public class FranchisePersistenceAdapter implements IFranchisePersistencePort {

    private final IFranchiseRepository franchiseRepository;
    private final IFranchiseEntityMapper franchiseEntityMapper;

    public FranchisePersistenceAdapter(IFranchiseRepository franchiseRepository, IFranchiseEntityMapper franchiseEntityMapper) {
        this.franchiseRepository = franchiseRepository;
        this.franchiseEntityMapper = franchiseEntityMapper;
    }

    @Override
    public Mono<Franchise> save(Franchise franchise) {
        return franchiseRepository.save(franchiseEntityMapper.toFranchiseEntity(franchise)).map(franchiseEntityMapper::toFranchiseModel);
    }

    @Override
    public Mono<Franchise> findById(Long id) {
        return franchiseRepository.findById(id).map(franchiseEntityMapper::toFranchiseModel);
    }

    @Override
    public Mono<Franchise> findByName(String franchiseName) {
        return franchiseRepository.findByName(franchiseName).map(franchiseEntityMapper::toFranchiseModel);
    }
}
