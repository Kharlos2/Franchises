package co.com.pragma.api.dtos.franchise;

import lombok.Data;

@Data
public class FranchiseSaveDto {

    private String name;

    public FranchiseSaveDto(String name) {
        this.name = name;
    }

    public FranchiseSaveDto() {
    }
}

