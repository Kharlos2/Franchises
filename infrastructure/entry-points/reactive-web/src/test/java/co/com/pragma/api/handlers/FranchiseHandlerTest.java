package co.com.pragma.api.handlers;

import co.com.pragma.api.dtos.franchise.FranchiseSaveDto;
import co.com.pragma.api.mappers.IFranchiseHandlerMapper;
import co.com.pragma.model.franchise.api.IFranchiseServicePort;
import co.com.pragma.model.franchise.models.Franchise;
import co.com.pragma.model.franchise.models.StockBranchProduct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FranchiseHandlerTest {

    @Mock
    private IFranchiseServicePort franchiseServicePort;

    @Mock
    private IFranchiseHandlerMapper franchiseHandlerMapper;

    @InjectMocks
    private FranchiseHandler franchiseHandler;

    private Franchise franchise;
    private FranchiseSaveDto franchiseSaveDto;

    @BeforeEach
    void setUp() {
        franchiseSaveDto = new FranchiseSaveDto("Franquicia Centro");
        franchise = new Franchise(1L, "Franquicia Centro");
    }

    @Test
    void save_WhenValidRequest_ShouldReturnCreatedFranchise() {
        // Arrange
        ServerRequest request = mock(ServerRequest.class);
        when(request.bodyToMono(FranchiseSaveDto.class)).thenReturn(Mono.just(franchiseSaveDto));

        when(franchiseHandlerMapper.toModel(franchiseSaveDto)).thenReturn(franchise);
        when(franchiseServicePort.save(franchise)).thenReturn(Mono.just(franchise));

        // Act
        Mono<ServerResponse> responseMono = franchiseHandler.save(request);

        // Assert
        StepVerifier.create(responseMono)
                .expectNextMatches(response -> response.statusCode().is2xxSuccessful())
                .verifyComplete();

        verify(request, times(1)).bodyToMono(FranchiseSaveDto.class);
        verify(franchiseHandlerMapper, times(1)).toModel(franchiseSaveDto);
        verify(franchiseServicePort, times(1)).save(franchise);
    }
    @Test
    void findTopProducts_WhenValidRequest_ShouldReturnProductsList() {
        // Arrange
        ServerRequest request = mock(ServerRequest.class);
        when(request.queryParam("franchiseId")).thenReturn(Optional.of("1"));

        StockBranchProduct product1 = new StockBranchProduct("1L", "Product 1", 10);
        StockBranchProduct product2 = new StockBranchProduct("2L", "Product 2", 5);
        when(franchiseServicePort.findProductsStock(1L)).thenReturn(Flux.just(product1,product2));

        // Act
        Mono<ServerResponse> responseMono = franchiseHandler.findTopProducts(request);

        // Assert
        StepVerifier.create(responseMono)
                .expectNextMatches(response -> response.statusCode().is2xxSuccessful())
                .verifyComplete();

        verify(request, times(1)).queryParam("franchiseId");
        verify(franchiseServicePort, times(1)).findProductsStock(1L);
    }
}
