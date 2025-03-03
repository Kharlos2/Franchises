package co.com.pragma.r2dbc.repositories;

import co.com.pragma.r2dbc.entities.FranchiseBranchEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface IFranchiseBranchRepository extends ReactiveCrudRepository<FranchiseBranchEntity,Long> {
}
