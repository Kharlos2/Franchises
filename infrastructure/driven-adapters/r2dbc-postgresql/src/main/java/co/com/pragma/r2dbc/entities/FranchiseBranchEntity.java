package co.com.pragma.r2dbc.entities;


import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@Table("public.franchise_branch")
public class FranchiseBranchEntity {

    @Id
    private Long id;
    @Column("franchise_id")
    private Long franchiseId;
    @Column("branch_id")
    private Long branchId;

    public FranchiseBranchEntity(Long id, Long franchiseId, Long branchId) {
        this.id = id;
        this.franchiseId = franchiseId;
        this.branchId = branchId;
    }

    public FranchiseBranchEntity() {
    }
}
