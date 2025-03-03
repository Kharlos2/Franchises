package co.com.pragma.model.franchise.api;

import co.com.pragma.model.franchise.models.Product;
import reactor.core.publisher.Mono;

public interface IProductServicePort {

    Mono<Product> save (Product product);

}
