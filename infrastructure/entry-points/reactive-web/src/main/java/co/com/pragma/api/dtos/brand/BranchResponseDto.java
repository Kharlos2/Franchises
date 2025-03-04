package co.com.pragma.api.dtos.brand;

import lombok.Data;

@Data
public class BranchResponseDto {

    private Long id;
    private String name;

    public BranchResponseDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public BranchResponseDto() {
    }
}
