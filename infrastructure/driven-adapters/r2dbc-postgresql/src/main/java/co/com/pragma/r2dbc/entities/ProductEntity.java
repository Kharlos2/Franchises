package co.com.pragma.r2dbc.entities;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@Table("public.products")
public class ProductEntity {

    @Id
    private Long id;
    private String name;
    private Integer stock;

    public ProductEntity(Long id, String name, Integer stock) {
        this.id = id;
        this.name = name;
        this.stock = stock;
    }

    public ProductEntity() {
    }
}
