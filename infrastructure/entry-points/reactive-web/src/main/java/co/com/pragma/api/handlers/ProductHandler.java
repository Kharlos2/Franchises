package co.com.pragma.api.handlers;

import co.com.pragma.api.dtos.brand.BranchResponseDto;
import co.com.pragma.api.dtos.brand.BranchSaveDto;
import co.com.pragma.api.dtos.product.ProductResponseDto;
import co.com.pragma.api.dtos.product.ProductSaveDto;
import co.com.pragma.api.mappers.IBranchHandlerMapper;
import co.com.pragma.api.mappers.IProductHandlerMapper;
import co.com.pragma.model.franchise.api.IBranchServicePort;
import co.com.pragma.model.franchise.api.IProductServicePort;
import co.com.pragma.model.franchise.models.Branch;
import co.com.pragma.model.franchise.models.Product;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class ProductHandler {

    private final IProductServicePort productServicePort;
    private final IProductHandlerMapper productHandlerMapper;

    public ProductHandler(IProductServicePort productServicePort, IProductHandlerMapper productHandlerMapper) {
        this.productServicePort = productServicePort;
        this.productHandlerMapper = productHandlerMapper;
    }

    public Mono<ServerResponse> save (ServerRequest request) {
        Mono<Product> productMono = request.bodyToMono(ProductSaveDto.class).map(productHandlerMapper::toModel);
        return productMono.flatMap(product ->
                ServerResponse.status(HttpStatusCode.valueOf(201))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(productServicePort.save(product)
                                .map(productHandlerMapper::toProductResponseDTO), ProductResponseDto.class)
        );
    }

}
