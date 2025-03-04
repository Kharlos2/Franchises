package co.com.pragma.api.dtos.product;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductSaveDto {

    private String name;
    private Integer stock;
    private Long branchId;

    public ProductSaveDto(String name, Integer stock, Long branchId) {
        this.name = name;
        this.stock = stock;
        this.branchId = branchId;
    }

    public ProductSaveDto() {
    }
}
