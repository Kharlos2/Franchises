package co.com.pragma.r2dbc.services;

import co.com.pragma.model.franchise.models.Branch;
import co.com.pragma.r2dbc.entities.BranchEntity;
import co.com.pragma.r2dbc.mappers.IBranchEntityMapper;
import co.com.pragma.r2dbc.repositories.IBranchRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class BranchPersistenceAdapterTest {

    @Mock
    private IBranchRepository branchRepository;

    @Mock
    private IBranchEntityMapper branchEntityMapper;

    @InjectMocks
    private BranchPersistenceAdapter branchPersistenceAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save_WhenBranchIsSaved_ShouldReturnBranch() {
        Branch branch = new Branch(1L, "Branch 1", 1L);
        BranchEntity branchEntity = new BranchEntity(1L, "Branch 1");

        when(branchEntityMapper.toBranchEntity(branch)).thenReturn(branchEntity);
        when(branchRepository.save(branchEntity)).thenReturn(Mono.just(branchEntity));
        when(branchEntityMapper.toBranchModel(branchEntity)).thenReturn(branch);

        StepVerifier.create(branchPersistenceAdapter.save(branch))
                .expectNextMatches(savedBranch -> savedBranch.getId().equals(1L))
                .verifyComplete();

        verify(branchRepository, times(1)).save(branchEntity);
    }

    @Test
    void findById_WhenBranchExists_ShouldReturnBranch() {
        BranchEntity branchEntity = new BranchEntity(1L, "Branch 1");
        Branch branch = new Branch(1L, "Branch 1", 1L);

        when(branchRepository.findById(1L)).thenReturn(Mono.just(branchEntity));
        when(branchEntityMapper.toBranchModel(branchEntity)).thenReturn(branch);

        StepVerifier.create(branchPersistenceAdapter.findById(1L))
                .expectNext(branch)
                .verifyComplete();

        verify(branchRepository, times(1)).findById(1L);
    }

    @Test
    void findById_WhenBranchDoesNotExist_ShouldReturnEmptyMono() {
        when(branchRepository.findById(1L)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(branchPersistenceAdapter.findById(1L))
                .verifyComplete();

        verify(branchRepository, times(1)).findById(1L);
    }

    @Test
    void findByName_WhenBranchExists_ShouldReturnBranch() {
        BranchEntity branchEntity = new BranchEntity(1L, "Branch 1");
        Branch branch = new Branch(1L, "Branch 1", 1L);

        when(branchRepository.findByName("Branch 1")).thenReturn(Mono.just(branchEntity));
        when(branchEntityMapper.toBranchModel(branchEntity)).thenReturn(branch);

        StepVerifier.create(branchPersistenceAdapter.findByName("Branch 1"))
                .expectNext(branch)
                .verifyComplete();

        verify(branchRepository, times(1)).findByName("Branch 1");
    }

    @Test
    void findByName_WhenBranchDoesNotExist_ShouldReturnEmptyMono() {
        when(branchRepository.findByName("Nonexistent Branch")).thenReturn(Mono.empty());

        StepVerifier.create(branchPersistenceAdapter.findByName("Nonexistent Branch"))
                .verifyComplete();

        verify(branchRepository, times(1)).findByName("Nonexistent Branch");
    }

    @Test
    void findBranchesByFranchiseId_WhenBranchesExist_ShouldReturnFlux() {
        BranchEntity branchEntity1 = new BranchEntity(1L, "Branch 1");
        BranchEntity branchEntity2 = new BranchEntity(2L, "Branch 2");
        Branch branch1 = new Branch(1L, "Branch 1", 1L);
        Branch branch2 = new Branch(2L, "Branch 2", 1L);

        when(branchRepository.findByFranchiseId(1L)).thenReturn(Flux.just(branchEntity1, branchEntity2));
        when(branchEntityMapper.toBranchModel(branchEntity1)).thenReturn(branch1);
        when(branchEntityMapper.toBranchModel(branchEntity2)).thenReturn(branch2);

        StepVerifier.create(branchPersistenceAdapter.findBranchesByFranchiseId(1L))
                .expectNext(branch1, branch2)
                .verifyComplete();

        verify(branchRepository, times(1)).findByFranchiseId(1L);
    }

    @Test
    void findBranchesByFranchiseId_WhenNoBranchesExist_ShouldReturnEmptyFlux() {
        when(branchRepository.findByFranchiseId(1L)).thenReturn(Flux.empty());

        StepVerifier.create(branchPersistenceAdapter.findBranchesByFranchiseId(1L))
                .verifyComplete();

        verify(branchRepository, times(1)).findByFranchiseId(1L);
    }
}

