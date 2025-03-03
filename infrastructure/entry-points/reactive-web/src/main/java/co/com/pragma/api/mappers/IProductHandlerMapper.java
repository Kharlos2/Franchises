package co.com.pragma.api.mappers;

import co.com.pragma.api.dtos.brand.BranchResponseDto;
import co.com.pragma.api.dtos.brand.BranchSaveDto;
import co.com.pragma.api.dtos.product.ProductResponseDto;
import co.com.pragma.api.dtos.product.ProductSaveDto;
import co.com.pragma.api.dtos.product.ProductUpdateStockDto;
import co.com.pragma.model.franchise.models.Branch;
import co.com.pragma.model.franchise.models.Product;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IProductHandlerMapper {

    Product toModel (ProductSaveDto productSaveDto);
    ProductResponseDto toProductResponseDTO(Product product);
    Product updateStockToModel (ProductUpdateStockDto productUpdateStockDto);
}
