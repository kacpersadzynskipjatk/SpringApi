package s28646.sricar.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarShopDetailsDto {
    private Long id;
    private String name;

    private Set<CarDto> cars;
}