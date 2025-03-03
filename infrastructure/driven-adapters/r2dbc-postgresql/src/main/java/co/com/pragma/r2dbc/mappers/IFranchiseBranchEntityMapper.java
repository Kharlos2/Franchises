package co.com.pragma.r2dbc.mappers;

import co.com.pragma.model.franchise.models.FranchiseBranch;
import co.com.pragma.r2dbc.entities.FranchiseBranchEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IFranchiseBranchEntityMapper {

    FranchiseBranchEntity toFranchiseBranchEntity (FranchiseBranch franchiseBranch);

}
