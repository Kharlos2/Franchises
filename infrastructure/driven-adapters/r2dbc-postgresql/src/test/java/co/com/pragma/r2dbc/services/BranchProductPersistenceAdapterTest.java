package co.com.pragma.r2dbc.services;


import co.com.pragma.model.franchise.models.BranchProduct;
import co.com.pragma.r2dbc.entities.BranchProductEntity;
import co.com.pragma.r2dbc.mappers.IBranchProductEntityMapper;
import co.com.pragma.r2dbc.repositories.IBranchProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class BranchProductPersistenceAdapterTest {
    @Mock
    private IBranchProductRepository branchProductRepository;

    @Mock
    private IBranchProductEntityMapper branchProductEntityMapper;

    @InjectMocks
    private BranchProductPersistenceAdapter branchProductPersistenceAdapter;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void save_WhenBranchProductIsSaved_ShouldCompleteSuccessfully() {

        BranchProduct branchProduct = new BranchProduct(1L, 1L);
        BranchProductEntity branchProductEntity = new BranchProductEntity(1L, 1L, 1L);

        when(branchProductEntityMapper.toEntity(branchProduct)).thenReturn(branchProductEntity);
        when(branchProductRepository.save(branchProductEntity)).thenReturn(Mono.empty());


        StepVerifier.create(branchProductPersistenceAdapter.save(branchProduct))
                .verifyComplete();

        verify(branchProductRepository, times(1)).save(branchProductEntity);
    }
}