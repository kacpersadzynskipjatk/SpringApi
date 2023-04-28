package s28646.sricar.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarShopDto extends RepresentationModel<CarShopDto> {

    private Long id;

    @NotBlank
    @Size(min = 2, message = "Name too short")
    @Size(max = 200, message = "Name too long")
    private String name;
}
