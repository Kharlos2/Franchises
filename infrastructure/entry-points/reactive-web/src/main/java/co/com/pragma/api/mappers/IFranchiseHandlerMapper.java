package co.com.pragma.api.mappers;

import co.com.pragma.api.dtos.franchise.FranchiseResponseDto;
import co.com.pragma.api.dtos.franchise.FranchiseSaveDto;
import co.com.pragma.model.franchise.models.Franchise;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IFranchiseHandlerMapper {

    Franchise toModel (FranchiseSaveDto franchiseSaveDto);
    FranchiseResponseDto toFranchiseResponseDTO (Franchise franchise);

}
