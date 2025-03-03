package co.com.pragma.r2dbc.mappers;

import co.com.pragma.model.franchise.models.Branch;
import co.com.pragma.r2dbc.entities.BranchEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IBranchEntityMapper {

    BranchEntity toBranchEntity(Branch branch);

    Branch toBranchModel(BranchEntity branchEntity);

}
