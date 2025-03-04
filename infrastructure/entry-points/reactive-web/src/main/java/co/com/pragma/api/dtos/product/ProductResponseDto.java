package co.com.pragma.api.dtos.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class ProductResponseDto {

    private Long id;
    private String name;
    private Integer stock;

    public ProductResponseDto(Long id, String name, Integer stock) {
        this.id = id;
        this.name = name;
        this.stock = stock;
    }

    public ProductResponseDto() {
    }
}
