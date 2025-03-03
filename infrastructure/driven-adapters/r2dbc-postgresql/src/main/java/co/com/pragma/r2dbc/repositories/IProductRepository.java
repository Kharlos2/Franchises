package co.com.pragma.r2dbc.repositories;

import co.com.pragma.r2dbc.entities.ProductEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface IProductRepository extends ReactiveCrudRepository<ProductEntity,Long> {

    @Query("SELECT * FROM public.products WHERE name ILIKE :name")
    Mono<ProductEntity> findByName(String productName);

}
