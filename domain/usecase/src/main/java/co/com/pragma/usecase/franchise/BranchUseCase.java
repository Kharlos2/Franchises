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
                .hasElement()
                .flatMap( existFranchise -> {
                    if (Boolean.FALSE.equals(existFranchise)){
                        return Mono.error(new CustomException(ExceptionsEnum.FRANCHISE_NOT_FOUND));
                    }
                    return branchPersistencePort.findByName(branch.getName())
                            .hasElement()
                            .flatMap(existBranch -> {
                                if(Boolean.TRUE.equals(existBranch)){
                                    Mono.error(new CustomException(ExceptionsEnum.ALREADY_EXIST_BRANCH));
                                }

                                Mono<Branch> branchMono = branchPersistencePort.save(branch);

                                branchMono.map(branchSaved ->
                                        franchiseBranchPersistencePort.save(new FranchiseBranch(branch.getFranchiseId(), branchSaved.getId()))
                                );

                                return branchMono;
                            });

                });
    }
}
