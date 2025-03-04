package co.com.pragma.model.franchise.spi;

import co.com.pragma.model.franchise.models.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IProductPersistencePort {

    Mono<Product> save (Product product);
    Mono<Product> findByName (String productName);
    Mono<Product> findById (Long productId);
    Mono<Void> deleteById(Long productId);
    Mono<Product> findTopProductByBranchId(Long branchId);
}
