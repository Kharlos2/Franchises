package co.com.pragma.model.franchise.api;

import co.com.pragma.model.franchise.models.Franchise;
import reactor.core.publisher.Mono;

public interface IFranchiseServicePort {

    Mono<Franchise> save (Franchise franchise);


}
