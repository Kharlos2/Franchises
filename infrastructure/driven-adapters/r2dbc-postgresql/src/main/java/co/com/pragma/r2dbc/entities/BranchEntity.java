package co.com.pragma.r2dbc.entities;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@Table("public.branches")
public class BranchEntity {

    @Id
    private Long id;
    private String name;

    public BranchEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public BranchEntity() {
    }
}
