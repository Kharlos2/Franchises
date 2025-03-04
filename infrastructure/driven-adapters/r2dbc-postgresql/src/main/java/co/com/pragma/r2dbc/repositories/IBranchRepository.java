package co.com.pragma.r2dbc.repositories;

import co.com.pragma.r2dbc.entities.BranchEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IBranchRepository extends ReactiveCrudRepository<BranchEntity, Long> {

    @Query("SELECT * FROM public.branches WHERE name ILIKE :name")
    Mono<BranchEntity> findByName(String branchName);

    @Query("""
    SELECT b.* FROM public.branches b
    JOIN public.franchise_branch fb ON b.id = fb.branch_id
    WHERE fb.franchise_id = :franchiseId
""")
    Flux<BranchEntity> findByFranchiseId(Long franchiseId);
}
