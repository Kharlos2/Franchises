package co.com.pragma.r2dbc.mappers;

import co.com.pragma.model.franchise.models.Product;
import co.com.pragma.r2dbc.entities.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IProductEntityMapper {

    ProductEntity toEntity (Product product);

    Product toModel(ProductEntity productEntity);

}
