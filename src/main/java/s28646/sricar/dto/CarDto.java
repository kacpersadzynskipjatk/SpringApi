package s28646.sricar.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarDto {
    private Long id;

    @NotBlank
    @Size(min = 2, max = 255)
    private String carBrand;
    @NotBlank
    private String carModel;
    private int carYear;
    private String carColor;
    private String carDrivetrain;
    private String carFuelType;
    private String carTransmission;
}
