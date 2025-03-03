package co.com.pragma.usecase.franchise;


import co.com.pragma.model.franchise.api.IFranchiseServicePort;
import co.com.pragma.model.franchise.exceptions.CustomException;
import co.com.pragma.model.franchise.exceptions.ExceptionsEnum;
import co.com.pragma.model.franchise.models.Franchise;
import co.com.pragma.model.franchise.spi.IFranchisePersistencePort;
import reactor.core.publisher.Mono;

public class FranchiseUseCase implements IFranchiseServicePort {

    private final IFranchisePersistencePort franchisePersistencePort;

    public FranchiseUseCase(IFranchisePersistencePort franchisePersistencePort) {
        this.franchisePersistencePort = franchisePersistencePort;
    }

    @Override
    public Mono<Franchise> save(Franchise franchise) {
        return franchisePersistencePort.findByName(franchise.getName())
                .hasElement()
                .flatMap( exist -> {
                        if (Boolean.TRUE.equals(exist)){
                           return Mono.error(new CustomException(ExceptionsEnum.ALREADY_EXIST_FRANCHISE));
                        }
                    return franchisePersistencePort.save(franchise);
                });

    }
}
