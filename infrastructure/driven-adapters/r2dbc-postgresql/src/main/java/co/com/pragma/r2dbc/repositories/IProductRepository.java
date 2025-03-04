package co.com.pragma.r2dbc.repositories;

import co.com.pragma.r2dbc.entities.ProductEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IProductRepository extends ReactiveCrudRepository<ProductEntity,Long> {

    @Query("SELECT * FROM public.products WHERE name ILIKE :name")
    Mono<ProductEntity> findByName(String productName);

    @Query("""
    SELECT p.* FROM public.products p
    JOIN public.branch_product bp ON p.id = bp.product_id
    WHERE bp.branch_id = :branchId
    ORDER BY p.stock DESC
    LIMIT 1
    """)
    Mono<ProductEntity> findTopProductByBranchId(Long branchId);
}
