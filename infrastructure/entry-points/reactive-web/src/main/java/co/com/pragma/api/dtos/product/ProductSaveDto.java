package co.com.pragma.api.dtos.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductSaveDto {

    private String name;
    private Integer stock;
    private Long branchId;

}
