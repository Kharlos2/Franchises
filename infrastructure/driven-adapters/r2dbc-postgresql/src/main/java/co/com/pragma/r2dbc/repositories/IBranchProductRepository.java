package co.com.pragma.r2dbc.repositories;

import co.com.pragma.r2dbc.entities.BranchProductEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface IBranchProductRepository extends ReactiveCrudRepository<BranchProductEntity, Long> {
}
