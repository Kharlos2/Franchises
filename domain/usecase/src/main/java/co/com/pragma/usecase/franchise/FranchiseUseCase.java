package co.com.pragma.usecase.franchise;


import co.com.pragma.model.franchise.api.IFranchiseServicePort;
import co.com.pragma.model.franchise.exceptions.CustomException;
import co.com.pragma.model.franchise.exceptions.ExceptionsEnum;
import co.com.pragma.model.franchise.models.Franchise;
import co.com.pragma.model.franchise.models.StockBranchProduct;
import co.com.pragma.model.franchise.spi.IBranchPersistencePort;
import co.com.pragma.model.franchise.spi.IFranchisePersistencePort;
import co.com.pragma.model.franchise.spi.IProductPersistencePort;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class FranchiseUseCase implements IFranchiseServicePort {

    private final IFranchisePersistencePort franchisePersistencePort;
    private final IBranchPersistencePort branchPersistencePort;
    private final IProductPersistencePort productPersistencePort;

    public FranchiseUseCase(IFranchisePersistencePort franchisePersistencePort, IBranchPersistencePort branchPersistencePort, IProductPersistencePort productPersistencePort) {
        this.franchisePersistencePort = franchisePersistencePort;
        this.branchPersistencePort = branchPersistencePort;
        this.productPersistencePort = productPersistencePort;
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
    @Override
    public Flux<StockBranchProduct> findProductsStock(Long franchiseId) {
        return franchisePersistencePort.findById(franchiseId)
                .switchIfEmpty(Mono.error(new CustomException(ExceptionsEnum.FRANCHISE_NOT_FOUND)))
                .flatMapMany(franchise -> branchPersistencePort.findBranchesByFranchiseId(franchiseId))
                .flatMap(branch -> productPersistencePort.findTopProductByBranchId(branch.getId())
                        .map(product -> new StockBranchProduct(branch.getName(), product.getName(), product.getStock()))
                );
    }
}
