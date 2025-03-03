package co.com.pragma.api.handlers;

import co.com.pragma.api.dtos.brand.BranchResponseDto;
import co.com.pragma.api.dtos.brand.BranchSaveDto;
import co.com.pragma.api.mappers.IBranchHandlerMapper;
import co.com.pragma.model.franchise.api.IBranchServicePort;
import co.com.pragma.model.franchise.models.Branch;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class ProductHandler {


    public ProductHandler(IBranchServicePort branchServicePort, IBranchHandlerMapper branchHandlerMapper) {
        this.branchServicePort = branchServicePort;
        this.branchHandlerMapper = branchHandlerMapper;
    }

    public Mono<ServerResponse> save (ServerRequest request) {
        Mono<Branch> branchMono = request.bodyToMono(BranchSaveDto.class).map(branchHandlerMapper::toModel);
        return branchMono.flatMap(branch ->
                ServerResponse.status(HttpStatusCode.valueOf(201))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(branchServicePort.save(branch)
                                .map(branchHandlerMapper::toBranchResponseDTO), BranchResponseDto.class)
        );
    }

}
