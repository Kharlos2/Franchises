package co.com.pragma.r2dbc.services;

import co.com.pragma.model.franchise.models.FranchiseBranch;
import co.com.pragma.model.franchise.spi.IFranchiseBranchPersistencePort;
import co.com.pragma.r2dbc.mappers.IFranchiseBranchEntityMapper;
import co.com.pragma.r2dbc.repositories.IFranchiseBranchRepository;
import reactor.core.publisher.Mono;

public class FranchiseBranchPersistenceAdapter implements IFranchiseBranchPersistencePort {

    private final IFranchiseBranchRepository franchiseBranchRepository;
    private final IFranchiseBranchEntityMapper franchiseBranchEntityMapper;

    public FranchiseBranchPersistenceAdapter(IFranchiseBranchRepository franchiseBranchRepository, IFranchiseBranchEntityMapper franchiseBranchEntityMapper) {
        this.franchiseBranchRepository = franchiseBranchRepository;
        this.franchiseBranchEntityMapper = franchiseBranchEntityMapper;
    }

    @Override
    public Mono<Void> save(FranchiseBranch franchiseBranch) {
        return franchiseBranchRepository.save(franchiseBranchEntityMapper.toFranchiseBranchEntity(franchiseBranch)).then();
    }
}
