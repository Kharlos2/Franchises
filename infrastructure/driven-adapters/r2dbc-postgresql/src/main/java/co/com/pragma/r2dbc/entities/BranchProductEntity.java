package co.com.pragma.r2dbc.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table("public.branch_product")
public class BranchProductEntity {
    @Id
    private Long id;
    @Column("branch_id")
    private Long branchId;
    @Column("product_id")
    private Long productId;
}
