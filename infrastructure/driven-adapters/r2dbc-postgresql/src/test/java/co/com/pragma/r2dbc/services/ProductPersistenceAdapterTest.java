package co.com.pragma.r2dbc.services;


import co.com.pragma.model.franchise.models.Product;
import co.com.pragma.r2dbc.entities.ProductEntity;
import co.com.pragma.r2dbc.mappers.IProductEntityMapper;
import co.com.pragma.r2dbc.repositories.IProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class ProductPersistenceAdapterTest {

    @Mock
    private IProductRepository productRepository;

    @Mock
    private IProductEntityMapper productEntityMapper;

    @InjectMocks
    private ProductPersistenceAdapter productPersistenceAdapter;

    private Product product;
    private ProductEntity productEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        product = new Product(1L, "Hamburguesa", 10, 5L);
        productEntity = new ProductEntity(1L, "Hamburguesa", 10);
    }

    @Test
    void save_WhenProductIsSaved_ShouldReturnProduct() {
        // Arrange
        when(productEntityMapper.toEntity(product)).thenReturn(productEntity);
        when(productRepository.save(productEntity)).thenReturn(Mono.just(productEntity));
        when(productEntityMapper.toModel(productEntity)).thenReturn(product);

        // Act & Assert
        StepVerifier.create(productPersistenceAdapter.save(product))
                .expectNext(product)
                .verifyComplete();

        verify(productRepository, times(1)).save(productEntity);
    }

    @Test
    void findByName_WhenProductExists_ShouldReturnProduct() {
        // Arrange
        when(productRepository.findByName("Hamburguesa")).thenReturn(Mono.just(productEntity));
        when(productEntityMapper.toModel(productEntity)).thenReturn(product);

        // Act & Assert
        StepVerifier.create(productPersistenceAdapter.findByName("Hamburguesa"))
                .expectNext(product)
                .verifyComplete();

        verify(productRepository, times(1)).findByName("Hamburguesa");
    }

    @Test
    void findById_WhenProductExists_ShouldReturnProduct() {
        // Arrange
        when(productRepository.findById(1L)).thenReturn(Mono.just(productEntity));
        when(productEntityMapper.toModel(productEntity)).thenReturn(product);

        // Act & Assert
        StepVerifier.create(productPersistenceAdapter.findById(1L))
                .expectNext(product)
                .verifyComplete();

        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void deleteById_WhenProductExists_ShouldDeleteProduct() {
        // Arrange
        when(productRepository.deleteById(1L)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(productPersistenceAdapter.deleteById(1L))
                .verifyComplete();

        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    void findTopProductByBranchId_WhenProductExists_ShouldReturnProduct() {
        // Arrange
        when(productRepository.findTopProductByBranchId(5L)).thenReturn(Mono.just(productEntity));
        when(productEntityMapper.toModel(productEntity)).thenReturn(product);

        // Act & Assert
        StepVerifier.create(productPersistenceAdapter.findTopProductByBranchId(5L))
                .expectNext(product)
                .verifyComplete();

        verify(productRepository, times(1)).findTopProductByBranchId(5L);
    }
}