package co.com.pragma.model.franchise.spi;

import co.com.pragma.model.franchise.models.Franchise;
import reactor.core.publisher.Mono;

public interface IFranchisePersistencePort {
    Mono<Franchise> save (Franchise franchise);
    Mono<Franchise> findById(Long id);
    Mono<Franchise> findByName(String franchiseName);
}
