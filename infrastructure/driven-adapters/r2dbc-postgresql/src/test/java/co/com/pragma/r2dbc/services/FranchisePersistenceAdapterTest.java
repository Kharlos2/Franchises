package co.com.pragma.r2dbc.services;


import co.com.pragma.model.franchise.models.Franchise;
import co.com.pragma.r2dbc.entities.FranchiseEntity;
import co.com.pragma.r2dbc.mappers.IFranchiseEntityMapper;
import co.com.pragma.r2dbc.repositories.IFranchiseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class FranchisePersistenceAdapterTest {

    @Mock
    private IFranchiseRepository franchiseRepository;

    @Mock
    private IFranchiseEntityMapper franchiseEntityMapper;

    @InjectMocks
    private FranchisePersistenceAdapter franchisePersistenceAdapter;

    private Franchise franchise;
    private FranchiseEntity franchiseEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        franchise = new Franchise(1L, "McBurger");
        franchiseEntity = new FranchiseEntity(1L, "McBurger");
    }

    @Test
    void save_WhenFranchiseIsSaved_ShouldReturnFranchise() {
        // Arrange
        when(franchiseEntityMapper.toFranchiseEntity(franchise)).thenReturn(franchiseEntity);
        when(franchiseRepository.save(franchiseEntity)).thenReturn(Mono.just(franchiseEntity));
        when(franchiseEntityMapper.toFranchiseModel(franchiseEntity)).thenReturn(franchise);

        // Act & Assert
        StepVerifier.create(franchisePersistenceAdapter.save(franchise))
                .expectNext(franchise)
                .verifyComplete();

        verify(franchiseRepository, times(1)).save(franchiseEntity);
    }

    @Test
    void findById_WhenFranchiseExists_ShouldReturnFranchise() {
        // Arrange
        when(franchiseRepository.findById(1L)).thenReturn(Mono.just(franchiseEntity));
        when(franchiseEntityMapper.toFranchiseModel(franchiseEntity)).thenReturn(franchise);

        // Act & Assert
        StepVerifier.create(franchisePersistenceAdapter.findById(1L))
                .expectNext(franchise)
                .verifyComplete();

        verify(franchiseRepository, times(1)).findById(1L);
    }

    @Test
    void findByName_WhenFranchiseExists_ShouldReturnFranchise() {
        // Arrange
        when(franchiseRepository.findByName("McBurger")).thenReturn(Mono.just(franchiseEntity));
        when(franchiseEntityMapper.toFranchiseModel(franchiseEntity)).thenReturn(franchise);

        // Act & Assert
        StepVerifier.create(franchisePersistenceAdapter.findByName("McBurger"))
                .expectNext(franchise)
                .verifyComplete();

        verify(franchiseRepository, times(1)).findByName("McBurger");
    }
}