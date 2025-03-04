package co.com.pragma.api.dtos.franchise;

import lombok.Data;

@Data
public class FranchiseResponseDto {
    private Long id;
    private String name;

    public FranchiseResponseDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public FranchiseResponseDto() {
    }
}
