package co.com.pragma.api.dtos.brand;

import lombok.Data;

@Data
public class BranchSaveDto {

    private String name;
    private Long franchiseId;


    public BranchSaveDto(String name, Long franchiseId) {
        this.name = name;
        this.franchiseId = franchiseId;
    }

    public BranchSaveDto() {
    }

}

