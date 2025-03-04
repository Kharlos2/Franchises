package co.com.pragma.usecase.franchise;

import co.com.pragma.model.franchise.exceptions.CustomException;
import co.com.pragma.model.franchise.exceptions.ExceptionsEnum;
import co.com.pragma.model.franchise.models.Branch;
import co.com.pragma.model.franchise.models.BranchProduct;
import co.com.pragma.model.franchise.models.Product;
import co.com.pragma.model.franchise.spi.IBranchPersistencePort;
import co.com.pragma.model.franchise.spi.IBranchProductPersistencePort;
import co.com.pragma.model.franchise.spi.IProductPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductUseCaseTest {


    @Mock
    private IProductPersistencePort productPersistencePort;

    @Mock
    private IBranchPersistencePort branchPersistencePort;

    @Mock
    private IBranchProductPersistencePort branchProductPersistencePort;

    @InjectMocks
    private ProductUseCase productUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save_WhenProductSavedSuccessfully_ShouldReturnProduct() {

        Product product = new Product(1L, "Product 1", 2,1L);
        Branch branch = new Branch(1L, "Branch 1",1L);
        Product savedProduct = new Product(1L, "Product 1",2,1L);

        when(branchPersistencePort.findById(product.getBranchId())).thenReturn(Mono.just(branch));
        when(productPersistencePort.findByName(product.getName())).thenReturn(Mono.empty());
        when(productPersistencePort.save(product)).thenReturn(Mono.just(savedProduct));
        when(branchProductPersistencePort.save(any(BranchProduct.class))).thenReturn(Mono.empty());


        StepVerifier.create(productUseCase.save(product))
                .expectNextMatches(response -> response.getId() == 1L && response.getBranchId() == 1L)
                .verifyComplete();

        verify(branchPersistencePort, times(1)).findById(product.getBranchId());
        verify(productPersistencePort, times(1)).findByName(product.getName());
        verify(productPersistencePort, times(1)).save(product);
        verify(branchProductPersistencePort, times(1)).save(any(BranchProduct.class));
    }

    @Test
    void save_WhenBranchNotFound_ShouldThrowException() {

        Product product = new Product(1L, "Product 1", 2, 1L);

        when(branchPersistencePort.findById(product.getBranchId())).thenReturn(Mono.empty());


        StepVerifier.create(productUseCase.save(product))
                .expectErrorMatches(error -> error instanceof CustomException &&
                        ((CustomException) error).getMessage().equals(ExceptionsEnum.BRANCH_NOT_FOUND.getMessage()))
                .verify();

        verify(branchPersistencePort, times(1)).findById(product.getBranchId());
        verify(productPersistencePort, never()).findByName(anyString());
        verify(productPersistencePort, never()).save(any());
        verify(branchProductPersistencePort, never()).save(any());
    }

    @Test
    void save_WhenProductAlreadyExists_ShouldThrowException() {

        Product product = new Product(1L, "Product 1", 2, 1L);
        Branch branch = new Branch(1L, "Branch 1",1L);
        Product existingProduct = new Product(2L, "Product 1", 2, 1L);

        when(branchPersistencePort.findById(product.getBranchId())).thenReturn(Mono.just(branch));
        when(productPersistencePort.findByName(product.getName())).thenReturn(Mono.just(existingProduct));

        StepVerifier.create(productUseCase.save(product))
                .expectErrorMatches(error -> error instanceof CustomException &&
                        ((CustomException) error).getMessage().equals(ExceptionsEnum.ALREADY_EXIST_PRODUCT.getMessage()))
                .verify();

        verify(branchPersistencePort, times(1)).findById(product.getBranchId());
        verify(productPersistencePort, times(1)).findByName(product.getName());
        verify(productPersistencePort, never()).save(any());
        verify(branchProductPersistencePort, never()).save(any());
    }
    @Test
    void deleteProductById_WhenProductExists_ShouldReturnSuccessMessage() {

        Long productId = 1L;
        Product product = new Product(1L, "Product 1", 1, 1L);

        when(productPersistencePort.findById(productId)).thenReturn(Mono.just(product));
        when(productPersistencePort.deleteById(productId)).thenReturn(Mono.empty());


        StepVerifier.create(productUseCase.deleteProductById(productId))
                .expectNextMatches(response -> response.getMessage().equals("Producto eliminado correctamente"))
                .verifyComplete();
    }

    @Test
    void deleteProductById_WhenProductDoesNotExist_ShouldReturnError() {

        Long productId = 1L;

        when(productPersistencePort.findById(productId)).thenReturn(Mono.empty());


        StepVerifier.create(productUseCase.deleteProductById(productId))
                .expectErrorMatches(throwable ->
                        throwable instanceof CustomException &&
                                ((CustomException) throwable).getMessage().equals(ExceptionsEnum.PRODUCT_NOT_FOUNT.getMessage()))
                .verify();
    }

    @Test
    void updateStock_WhenProductExists_ShouldUpdateStock() {
        // Arrange
        Product existingProduct = new Product(1L, "Product 1",2, 1L);
        Product updatedProduct = new Product(1L, "Product 1", 2,1L);
        updatedProduct.setStock(50);

        when(productPersistencePort.findById(1L)).thenReturn(Mono.just(existingProduct));
        when(productPersistencePort.save(any(Product.class))).thenReturn(Mono.just(updatedProduct));

        // Act & Assert
        StepVerifier.create(productUseCase.updateStock(updatedProduct))
                .expectNextMatches(product -> product.getStock() == 50)
                .verifyComplete();
    }

    @Test
    void updateStock_WhenProductDoesNotExist_ShouldReturnError() {
        // Arrange
        Product product = new Product(1L, "Product 1", 2, 1L);
        product.setStock(50);

        when(productPersistencePort.findById(1L)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(productUseCase.updateStock(product))
                .expectErrorMatches(throwable ->
                        throwable instanceof CustomException &&
                                ((CustomException) throwable).getMessage().equals(ExceptionsEnum.PRODUCT_NOT_FOUNT.getMessage()))
                .verify();
    }

}