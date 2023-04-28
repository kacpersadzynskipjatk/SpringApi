package s28646.sricar.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.PathVariable;
import s28646.sricar.model.Car;

import java.util.List;

public interface CarRepository extends CrudRepository<Car, Long> {
    List<Car> findAll();
    @Query("select cs.cars from CarShop as cs where cs.id = :carShopId")
    List<Car> findCarsByCarShopId(@PathVariable Long carShopId);
}
