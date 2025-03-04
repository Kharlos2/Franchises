package co.com.pragma.r2dbc.services;

import co.com.pragma.model.franchise.models.FranchiseBranch;
import co.com.pragma.r2dbc.entities.FranchiseBranchEntity;
import co.com.pragma.r2dbc.mappers.IFranchiseBranchEntityMapper;
import co.com.pragma.r2dbc.repositories.IFranchiseBranchRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class FranchiseBranchPersistenceAdapterTest {

    @Mock
    private IFranchiseBranchRepository franchiseBranchRepository;

    @Mock
    private IFranchiseBranchEntityMapper franchiseBranchEntityMapper;

    @InjectMocks
    private FranchiseBranchPersistenceAdapter franchiseBranchPersistenceAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save_WhenFranchiseBranchIsSaved_ShouldCompleteSuccessfully() {
        // Arrange
        FranchiseBranch franchiseBranch = new FranchiseBranch(1L, 1L);
        FranchiseBranchEntity franchiseBranchEntity = new FranchiseBranchEntity(1L, 1L, 1L);

        when(franchiseBranchEntityMapper.toFranchiseBranchEntity(franchiseBranch)).thenReturn(franchiseBranchEntity);
        when(franchiseBranchRepository.save(franchiseBranchEntity)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(franchiseBranchPersistenceAdapter.save(franchiseBranch))
                .verifyComplete(); // Verifica que no hay errores y finaliza correctamente

        verify(franchiseBranchRepository, times(1)).save(franchiseBranchEntity);
    }
}