package co.com.pragma.usecase.franchise;

import co.com.pragma.model.franchise.api.IBranchServicePort;
import co.com.pragma.model.franchise.exceptions.CustomException;
import co.com.pragma.model.franchise.models.Branch;
import co.com.pragma.model.franchise.models.FranchiseBranch;
import co.com.pragma.model.franchise.spi.IBranchPersistencePort;
import co.com.pragma.model.franchise.spi.IFranchiseBranchPersistencePort;
import co.com.pragma.model.franchise.spi.IFranchisePersistencePort;
import reactor.core.publisher.Mono;

import co.com.pragma.model.franchise.exceptions.ExceptionsEnum;

public class BranchUseCase implements IBranchServicePort {

    private final IBranchPersistencePort branchPersistencePort;
    private final IFranchiseBranchPersistencePort franchiseBranchPersistencePort;
    private final IFranchisePersistencePort franchisePersistencePort;

    public BranchUseCase(IBranchPersistencePort branchPersistencePort, IFranchiseBranchPersistencePort franchiseBranchPersistencePort, IFranchisePersistencePort franchisePersistencePort) {
        this.branchPersistencePort = branchPersistencePort;
        this.franchiseBranchPersistencePort = franchiseBranchPersistencePort;
        this.franchisePersistencePort = franchisePersistencePort;
    }

    @Override
    public Mono<Branch> save(Branch branch) {
        return franchisePersistencePort.findById(branch.getFranchiseId())
                .switchIfEmpty(Mono.error(new CustomException(ExceptionsEnum.FRANCHISE_NOT_FOUND)))
                .flatMap(franchise ->
                        branchPersistencePort.findByName(branch.getName())
                                .flatMap(existingBranch -> Mono.error(new CustomException(ExceptionsEnum.ALREADY_EXIST_BRANCH)))
                                .then(Mono.defer(() -> branchPersistencePort.save(branch)))
                )
                .flatMap(branchSaved ->
                        franchiseBranchPersistencePort.save(new FranchiseBranch(branch.getFranchiseId(), branchSaved.getId()))
                                .thenReturn(branchSaved)
                );
    }
}
