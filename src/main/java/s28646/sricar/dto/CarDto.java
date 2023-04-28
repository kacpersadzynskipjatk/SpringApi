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

    @NotBlank(message = "Car Brand is required")
    @Size(min = 2, message = "Car Brand too short")
    @Size(max = 200, message = "Car Brand too long")
    private String carBrand;
    @NotBlank(message = "Car Model is required")
    private String carModel;
    private int carYear;
    private String carColor;
    private String carDrivetrain;
    private String carFuelType;
    private String carTransmission;
}
