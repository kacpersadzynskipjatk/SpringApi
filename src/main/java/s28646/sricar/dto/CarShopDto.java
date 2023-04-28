package s28646.sricar.dto;

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
    private String name;
}
