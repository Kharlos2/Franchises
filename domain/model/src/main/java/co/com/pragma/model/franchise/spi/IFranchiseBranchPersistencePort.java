package co.com.pragma.model.franchise.spi;

import co.com.pragma.model.franchise.models.FranchiseBranch;
import reactor.core.publisher.Mono;

public interface IFranchiseBranchPersistencePort {

    Mono<Void> save (FranchiseBranch franchiseBranch);

}
