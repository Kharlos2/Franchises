package co.com.pragma.model.franchise.api;

import co.com.pragma.model.franchise.models.Franchise;
import co.com.pragma.model.franchise.models.StockBranchProduct;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IFranchiseServicePort {

    Mono<Franchise> save (Franchise franchise);
    Flux<StockBranchProduct> findProductsStock(Long franchiseId);

}
