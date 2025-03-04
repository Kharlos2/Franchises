package co.com.pragma.usecase.franchise;

import co.com.pragma.model.franchise.exceptions.CustomException;
import co.com.pragma.model.franchise.exceptions.ExceptionsEnum;
import co.com.pragma.model.franchise.models.Branch;
import co.com.pragma.model.franchise.models.Franchise;
import co.com.pragma.model.franchise.models.FranchiseBranch;
import co.com.pragma.model.franchise.spi.IBranchPersistencePort;
import co.com.pragma.model.franchise.spi.IFranchiseBranchPersistencePort;
import co.com.pragma.model.franchise.spi.IFranchisePersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class BranchUseCaseTest {

    @Mock
    private IBranchPersistencePort branchPersistencePort;

    @Mock
    private IFranchiseBranchPersistencePort franchiseBranchPersistencePort;

    @Mock
    private IFranchisePersistencePort franchisePersistencePort;

    @InjectMocks
    private BranchUseCase branchUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save_WhenFranchiseNotFound_ShouldReturnError() {
        Branch branch = new Branch(1L, "Branch 1", 1L);
        Branch savedBranch = new Branch(1L, "Branch 1", 1L);

        when(franchisePersistencePort.findById(branch.getFranchiseId())).thenReturn(Mono.empty());
        when(branchPersistencePort.findByName(branch.getName())).thenReturn(Mono.empty());
        when(branchPersistencePort.save(branch)).thenReturn(Mono.just(savedBranch));

        Mono<Branch> branchMono = branchUseCase.save(branch);


        StepVerifier.create(branchMono)
                .expectErrorMatches(error ->
                     error instanceof CustomException customException &&
                             customException.getMessage().equals(ExceptionsEnum.FRANCHISE_NOT_FOUND.getMessage())
                )
                .verify();

        verify(franchisePersistencePort, times(1)).findById(branch.getFranchiseId());
        verifyNoInteractions(branchPersistencePort, franchiseBranchPersistencePort,branchPersistencePort);
    }

    @Test
    void save_WhenBranchAlreadyExists_ShouldReturnError() {
        Branch branch = new Branch(1L, "Branch 1", 1L);
        Franchise franchise = new Franchise(1L, "Franchise 1");
        when(franchisePersistencePort.findById(branch.getFranchiseId())).thenReturn(Mono.just(franchise));
        when(branchPersistencePort.findByName(branch.getName())).thenReturn(Mono.just(branch));

        Mono<Branch> branchMono = branchUseCase.save(branch);

        StepVerifier.create(branchMono)
                .expectErrorMatches(error ->
                        error instanceof CustomException customException &&
                                customException.getMessage().equals(ExceptionsEnum.ALREADY_EXIST_BRANCH.getMessage())
                )
                .verify();

        verify(franchisePersistencePort, times(1)).findById(branch.getFranchiseId());
        verify(branchPersistencePort, times(1)).findByName(branch.getName());
        verifyNoMoreInteractions(branchPersistencePort, franchiseBranchPersistencePort);
    }

    @Test
    void save_WhenBranchSavedSuccessfully_ShouldReturnBranch() {
        Branch branch = new Branch(1L, "Branch 1", 1L);
        Franchise franchise = new Franchise(1L, "Franchise 1");
        Branch savedBranch = new Branch(1L, "Branch 1", 1L);

        when(franchisePersistencePort.findById(branch.getFranchiseId())).thenReturn(Mono.just(franchise));
        when(branchPersistencePort.findByName(branch.getName())).thenReturn(Mono.empty());
        when(branchPersistencePort.save(branch)).thenReturn(Mono.just(savedBranch));
        when(franchiseBranchPersistencePort.save(any(FranchiseBranch.class))).thenReturn(Mono.empty());

        StepVerifier.create(branchUseCase.save(branch))
                .expectNextMatches(response ->
                        response.getId() == 1L &&
                        response.getFranchiseId() == 1L)
                .verifyComplete();

        verify(franchisePersistencePort, times(1)).findById(branch.getFranchiseId());
        verify(branchPersistencePort, times(1)).findByName(branch.getName());
        verify(branchPersistencePort, times(1)).save(branch);
        verify(franchiseBranchPersistencePort, times(1)).save(any(FranchiseBranch.class));
    }
}