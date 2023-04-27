package s28646.sricar.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String carBrand;
    private String carModel;
    private int carYear;
    private String carColor;
    private String carDrivetrain;
    private String carFuelType;
    private String carTransmission;

    @ManyToOne
    @JoinColumn(name="car_id")
    private CarShop car;
}
