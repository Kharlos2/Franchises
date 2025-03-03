package co.com.pragma.r2dbc.mappers;

import co.com.pragma.model.franchise.models.BranchProduct;
import co.com.pragma.r2dbc.entities.BranchProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IBranchProductEntityMapper {

    BranchProductEntity toEntity (BranchProduct branchProduct);

}
