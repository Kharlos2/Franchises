package co.com.pragma.usecase.franchise;

import co.com.pragma.model.franchise.exceptions.CustomException;
import co.com.pragma.model.franchise.exceptions.ExceptionsEnum;
import co.com.pragma.model.franchise.models.Branch;
import co.com.pragma.model.franchise.models.Franchise;
import co.com.pragma.model.franchise.models.Product;
import co.com.pragma.model.franchise.models.StockBranchProduct;
import co.com.pragma.model.franchise.spi.IBranchPersistencePort;
import co.com.pragma.model.franchise.spi.IFranchisePersistencePort;
import co.com.pragma.model.franchise.spi.IProductPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FranchiseUseCaseTest {
    private IFranchisePersistencePort franchisePersistencePort;
    private IBranchPersistencePort branchPersistencePort;
    private IProductPersistencePort productPersistencePort;
    private FranchiseUseCase franchiseUseCase;

    @BeforeEach
    void setUp() {
        franchisePersistencePort = mock(IFranchisePersistencePort.class);
        branchPersistencePort = mock(IBranchPersistencePort.class);
        productPersistencePort = mock(IProductPersistencePort.class);
        franchiseUseCase = new FranchiseUseCase(franchisePersistencePort, branchPersistencePort, productPersistencePort);
    }

    @Test
    void save_WhenFranchiseAlreadyExists_ShouldReturnError() {
        Franchise franchise = new Franchise(1L, "Franchise A");
        when(franchisePersistencePort.findByName(franchise.getName())).thenReturn(Mono.just(franchise));

        StepVerifier.create(franchiseUseCase.save(franchise))
                .expectErrorMatches(error -> error instanceof CustomException &&
                        ((CustomException) error).getMessage().equals(ExceptionsEnum.ALREADY_EXIST_FRANCHISE.getMessage()))
                .verify();
    }

    @Test
    void save_WhenFranchiseDoesNotExist_ShouldSaveSuccessfully() {
        Franchise franchise = new Franchise(1L, "Franchise A");
        when(franchisePersistencePort.findByName(franchise.getName())).thenReturn(Mono.empty());
        when(franchisePersistencePort.save(franchise)).thenReturn(Mono.just(franchise));

        StepVerifier.create(franchiseUseCase.save(franchise))
                .expectNext(franchise)
                .verifyComplete();
    }

    @Test
    void findProductsStock_WhenFranchiseNotFound_ShouldReturnError() {
        when(franchisePersistencePort.findById(1L)).thenReturn(Mono.empty());

        Flux<StockBranchProduct> stockBranchProductFlux = franchiseUseCase.findProductsStock(1L);
        StepVerifier.create(stockBranchProductFlux)
                .expectErrorMatches(error -> error instanceof CustomException &&
                        error.getMessage().equals(ExceptionsEnum.FRANCHISE_NOT_FOUND.getMessage()))
                .verify();
    }

    @Test
    void findProductsStock_WhenFranchiseExists_ShouldReturnStock() {
        Franchise franchise = new Franchise(1L, "Franchise A");
        Branch branch = new Branch(1L, "Branch A", 1L);
        Product product = new Product(1L, "Product A", 100,1L);

        when(franchisePersistencePort.findById(1L)).thenReturn(Mono.just(franchise));
        when(branchPersistencePort.findBranchesByFranchiseId(1L)).thenReturn(Flux.just(branch));
        when(productPersistencePort.findTopProductByBranchId(1L)).thenReturn(Mono.just(product));

        StepVerifier.create(franchiseUseCase.findProductsStock(1L))
                .expectNextMatches(stockBranchProduct1 ->
                        stockBranchProduct1.getProductName().equals("Product A"))
                .verifyComplete();
    }

}