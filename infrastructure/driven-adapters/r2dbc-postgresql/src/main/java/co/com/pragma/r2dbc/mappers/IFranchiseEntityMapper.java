package co.com.pragma.r2dbc.mappers;

import co.com.pragma.model.franchise.models.Franchise;
import co.com.pragma.r2dbc.entities.FranchiseEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IFranchiseEntityMapper {

    FranchiseEntity toFranchiseEntity(Franchise franchise);

    Franchise toFranchiseModel(FranchiseEntity franchiseEntity);

}
