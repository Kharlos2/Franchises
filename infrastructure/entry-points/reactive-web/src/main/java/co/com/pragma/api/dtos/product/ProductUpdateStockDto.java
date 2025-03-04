package co.com.pragma.api.dtos.product;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductUpdateStockDto {

    private Long id;
    private Integer stock;

    public ProductUpdateStockDto(Long id, Integer stock) {
        this.id = id;
        this.stock = stock;
    }

    public ProductUpdateStockDto() {
    }
}
