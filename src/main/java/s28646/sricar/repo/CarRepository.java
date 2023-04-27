package s28646.sricar.repo;

import org.springframework.data.repository.CrudRepository;
import s28646.sricar.model.Car;

import java.util.List;

public interface CarRepository extends CrudRepository<Car, Long> {
    List<Car> findAll();
}
