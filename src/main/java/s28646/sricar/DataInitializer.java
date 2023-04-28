package s28646.sricar;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import s28646.sricar.model.Car;
import s28646.sricar.model.CarShop;
import s28646.sricar.repo.CarRepository;
import s28646.sricar.repo.CarShopRepository;

import java.util.Arrays;
import java.util.HashSet;

@Component
public class DataInitializer implements ApplicationRunner {

    private CarRepository carRepository;
    private CarShopRepository carShopRepository;

    public DataInitializer(CarRepository carRepository, CarShopRepository carShopRepository){
        this.carRepository = carRepository;
        this.carShopRepository = carShopRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Car c1 = Car.builder()
                .carBrand("Fiat")
                .carModel("Punto")
                .carYear(2005)
                .carColor("Red")
                .carDrivetrain("FWD")
                .carFuelType("Gasoline")
                .carTransmission("Manual")
                .build();
        Car c2 = Car.builder()
                .carBrand("Toyota")
                .carModel("Yaris")
                .carYear(2008)
                .carColor("Black")
                .carDrivetrain("FWD")
                .carFuelType("Gasoline")
                .carTransmission("Manual")
                .build();
        Car c3 = Car.builder()
                .carBrand("Opel")
                .carModel("Astra")
                .carYear(2007)
                .carColor("Orange")
                .carDrivetrain("RWD")
                .carFuelType("Gasoline")
                .carTransmission("Manual")
                .build();

        CarShop cs1 = CarShop.builder().name("FreshCars").cars(new HashSet<>()).build();
        CarShop cs2 = CarShop.builder().name("FinestCars").cars(new HashSet<>()).build();

        c1.setCarShop(cs1);
        cs1.getCars().add(c1);

        c2.setCarShop(cs1);
        cs1.getCars().add(c2);

        c3.setCarShop(cs2);
        cs2.getCars().add(c3);

        carShopRepository.saveAll(Arrays.asList(cs1, cs2));
        carRepository.saveAll(Arrays.asList(c1, c2, c3));
    }
}
