package co.com.pragma.api.dtos.brand;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BranchResponseDto {

    private Long id;
    private String name;
    private Long franchiseId;

}
