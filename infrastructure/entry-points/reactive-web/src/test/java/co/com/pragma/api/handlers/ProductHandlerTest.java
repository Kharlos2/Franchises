package co.com.pragma.api.handlers;

import co.com.pragma.api.dtos.product.ProductSaveDto;
import co.com.pragma.api.dtos.product.ProductUpdateStockDto;
import co.com.pragma.api.mappers.IProductHandlerMapper;
import co.com.pragma.model.franchise.api.IProductServicePort;
import co.com.pragma.model.franchise.models.DeleteResponse;
import co.com.pragma.model.franchise.models.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductHandlerTest {

    @Mock
    private IProductServicePort productServicePort;

    @Mock
    private IProductHandlerMapper productHandlerMapper;

    @InjectMocks
    private ProductHandler productHandler;

    private Product product;
    private ProductSaveDto productSaveDto;

    @BeforeEach
    void setUp() {
        productSaveDto = new ProductSaveDto("Producto 1", 100,1L);
        product = new Product(1L, "Producto 1", 100, 1L);
    }

    @Test
    void save_WhenValidRequest_ShouldReturnCreatedProduct() {
        // Arrange
        ServerRequest request = mock(ServerRequest.class);
        when(request.bodyToMono(ProductSaveDto.class)).thenReturn(Mono.just(productSaveDto));

        when(productHandlerMapper.toModel(productSaveDto)).thenReturn(product);
        when(productServicePort.save(product)).thenReturn(Mono.just(product));

        // Act
        Mono<ServerResponse> responseMono = productHandler.save(request);

        // Assert
        StepVerifier.create(responseMono)
                .expectNextMatches(response -> response.statusCode().is2xxSuccessful())
                .verifyComplete();

        verify(request, times(1)).bodyToMono(ProductSaveDto.class);
        verify(productHandlerMapper, times(1)).toModel(productSaveDto);
        verify(productServicePort, times(1)).save(product);
    }

    @Test
    void delete_WhenValidRequest_ShouldReturnOk() {
        // Arrange
        ServerRequest request = mock(ServerRequest.class);
        when(request.queryParam("productId")).thenReturn(Optional.of("1"));

        DeleteResponse deleteResponse = new DeleteResponse("Producto eliminado");
        when(productServicePort.deleteProductById(1L)).thenReturn(Mono.just(deleteResponse));

        // Act
        Mono<ServerResponse> responseMono = productHandler.delete(request);

        // Assert
        StepVerifier.create(responseMono)
                .expectNextMatches(response -> response.statusCode().is2xxSuccessful())
                .verifyComplete();

        verify(request, times(1)).queryParam("productId");
        verify(productServicePort, times(1)).deleteProductById(1L);
    }
    @Test
    void updateStock_WhenValidRequest_ShouldReturnUpdatedProduct() {
        // Arrange
        ServerRequest request = mock(ServerRequest.class);
        ProductUpdateStockDto updateStockDto = new ProductUpdateStockDto(1L, 50);
        Product updatedProduct = new Product(1L, "Producto 1", 50,1L);

        when(request.bodyToMono(ProductUpdateStockDto.class)).thenReturn(Mono.just(updateStockDto));
        when(productHandlerMapper.updateStockToModel(updateStockDto)).thenReturn(updatedProduct);
        when(productServicePort.updateStock(updatedProduct)).thenReturn(Mono.just(updatedProduct));

        // Act
        Mono<ServerResponse> responseMono = productHandler.updateStock(request);

        // Assert
        StepVerifier.create(responseMono)
                .expectNextMatches(response -> response.statusCode().is2xxSuccessful())
                .verifyComplete();

        verify(request, times(1)).bodyToMono(ProductUpdateStockDto.class);
        verify(productHandlerMapper, times(1)).updateStockToModel(updateStockDto);
        verify(productServicePort, times(1)).updateStock(updatedProduct);
    }


}