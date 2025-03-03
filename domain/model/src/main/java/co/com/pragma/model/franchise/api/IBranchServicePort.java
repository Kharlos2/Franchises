package co.com.pragma.model.franchise.api;

import co.com.pragma.model.franchise.models.Branch;
import reactor.core.publisher.Mono;

public interface IBranchServicePort {

    Mono<Branch> save (Branch branch);

}
