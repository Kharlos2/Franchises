package co.com.pragma.api.mappers;

import co.com.pragma.api.dtos.brand.BranchResponseDto;
import co.com.pragma.api.dtos.brand.BranchSaveDto;
import co.com.pragma.model.franchise.models.Branch;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IBranchHandlerMapper {

    Branch toModel (BranchSaveDto branchSaveDto);
    BranchResponseDto toBranchResponseDTO(Branch branch);

}
