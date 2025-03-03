package co.com.pragma.model.franchise.spi;

import co.com.pragma.model.franchise.models.Branch;
import reactor.core.publisher.Mono;

import java.awt.*;

public interface IBranchPersistencePort {

    Mono<Branch> save (Branch branch);
    Mono<Branch> findById(Long id);
    Mono<Branch> findByName(String name);
}
