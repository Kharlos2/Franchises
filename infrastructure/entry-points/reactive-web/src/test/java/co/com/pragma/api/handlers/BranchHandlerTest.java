package co.com.pragma.api.handlers;

import co.com.pragma.api.dtos.brand.BranchResponseDto;
import co.com.pragma.api.dtos.brand.BranchSaveDto;
import co.com.pragma.api.mappers.IBranchHandlerMapper;
import co.com.pragma.model.franchise.api.IBranchServicePort;
import co.com.pragma.model.franchise.models.Branch;
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

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BranchHandlerTest {

    @Mock
    private IBranchServicePort branchServicePort;

    @Mock
    private IBranchHandlerMapper branchHandlerMapper;

    @InjectMocks
    private BranchHandler branchHandler;

    private Branch branch;
    private BranchSaveDto branchSaveDto;

    @BeforeEach
    void setUp() {
        branchSaveDto = new BranchSaveDto("Sucursal Centro", 1L);
        branch = new Branch(1L, "Sucursal Centro", 1L);
    }

    @Test
    void save_WhenValidRequest_ShouldReturnCreatedBranch() {
        ServerRequest request = mock(ServerRequest.class);
        when(request.bodyToMono(BranchSaveDto.class)).thenReturn(Mono.just(branchSaveDto));

        when(branchHandlerMapper.toModel(branchSaveDto)).thenReturn(branch);
        when(branchServicePort.save(branch)).thenReturn(Mono.just(branch));

        Mono<ServerResponse> responseMono = branchHandler.save(request);

        StepVerifier.create(responseMono)
                .expectNextMatches(response ->
                    response.statusCode().is2xxSuccessful()
                )
                .verifyComplete();

        verify(request, times(1)).bodyToMono(BranchSaveDto.class);
        verify(branchHandlerMapper, times(1)).toModel(branchSaveDto);
        verify(branchServicePort, times(1)).save(branch);
    }
}