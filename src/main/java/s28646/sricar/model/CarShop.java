package s28646.sricar.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarShop {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "carShop")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Car> cars;
}
