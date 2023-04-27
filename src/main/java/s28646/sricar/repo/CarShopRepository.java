package s28646.sricar.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import s28646.sricar.model.CarShop;

import java.util.List;
import java.util.Optional;

public interface CarShopRepository extends CrudRepository<CarShop, Long> {
    List<CarShop> findAll();

    @Query("from CarShop as cs left join fetch cs.cars where cs.id=:carShopId")
    Optional<CarShop> getCarShopDetails(@Param("carShopId") Long carShopId);
}
