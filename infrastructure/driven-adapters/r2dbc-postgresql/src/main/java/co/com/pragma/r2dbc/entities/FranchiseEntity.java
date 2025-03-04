package co.com.pragma.r2dbc.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@Table("public.franchises")
public class FranchiseEntity {

    @Id
    private Long id;
    private String name;

    public FranchiseEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public FranchiseEntity() {
    }
}
