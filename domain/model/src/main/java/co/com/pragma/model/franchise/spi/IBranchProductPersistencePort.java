package co.com.pragma.model.franchise.spi;

import co.com.pragma.model.franchise.models.BranchProduct;
import reactor.core.publisher.Mono;

public interface IBranchProductPersistencePort {

    Mono<Void> save (BranchProduct branchProduct);

}
