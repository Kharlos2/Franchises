package co.com.pragma.api.handlers;

import co.com.pragma.api.dtos.product.ProductResponseDto;
import co.com.pragma.api.dtos.product.ProductSaveDto;
import co.com.pragma.api.dtos.product.ProductUpdateStockDto;
import co.com.pragma.api.mappers.IProductHandlerMapper;
import co.com.pragma.model.franchise.api.IProductServicePort;
import co.com.pragma.model.franchise.models.DeleteResponse;
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

    public Mono<ServerResponse> delete(ServerRequest request) {
        Long productId = Long.parseLong(String.valueOf(request.queryParam("productId").orElse("0")));

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(productServicePort.deleteProductById(productId), DeleteResponse.class);
    }


    public Mono<ServerResponse> updateStock(ServerRequest request) {

        Mono<Product> productMono = request.bodyToMono(ProductUpdateStockDto.class).map(productHandlerMapper::updateStockToModel);
        return productMono.flatMap(product ->
                ServerResponse.status(HttpStatusCode.valueOf(200))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(productServicePort.updateStock(product)
                                .map(productHandlerMapper::toProductResponseDTO), ProductResponseDto.class)
        );
    }

}
