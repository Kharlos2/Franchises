package co.com.pragma.api.handlers;

import co.com.pragma.api.dtos.franchise.FranchiseResponseDto;
import co.com.pragma.api.dtos.franchise.FranchiseSaveDto;
import co.com.pragma.api.mappers.IFranchiseHandlerMapper;
import co.com.pragma.model.franchise.api.IFranchiseServicePort;
import co.com.pragma.model.franchise.models.Franchise;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class FranchiseHandler {

    private final IFranchiseServicePort franchiseServicePort;
    private final IFranchiseHandlerMapper franchiseHandlerMapper;

    public FranchiseHandler(IFranchiseServicePort franchiseServicePort, IFranchiseHandlerMapper franchiseHandlerMapper) {
        this.franchiseServicePort = franchiseServicePort;
        this.franchiseHandlerMapper = franchiseHandlerMapper;
    }

    public Mono<ServerResponse> save (ServerRequest request) {
        Mono<Franchise> franchiseMono = request.bodyToMono(FranchiseSaveDto.class).map(franchiseHandlerMapper::toModel);
        return franchiseMono.flatMap(franchise ->
                ServerResponse.status(HttpStatusCode.valueOf(201))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(franchiseServicePort.save(franchise)
                                .map(franchiseHandlerMapper::toFranchiseResponseDTO), FranchiseResponseDto.class)
        );
    }

}
