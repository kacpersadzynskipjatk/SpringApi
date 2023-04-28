package s28646.sricar.rest;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import s28646.sricar.dto.CarDetailsDto;
import s28646.sricar.dto.CarDto;
import s28646.sricar.model.Car;
import s28646.sricar.model.CarShop;
import s28646.sricar.repo.CarRepository;

import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cars")
public class CarController {
    private final CarRepository carRepository;
    private final ModelMapper modelMapper;

    public CarController(CarRepository carRepository, ModelMapper modelMapper){
        this.carRepository = carRepository;
        this.modelMapper = modelMapper;
    }

    private CarDto convertToDto(Car e) {
        return modelMapper.map(e, CarDto.class);
    }
    private CarDetailsDto convertToDetailsDto(Car e) {
        return modelMapper.map(e, CarDetailsDto.class);
    }
    private Car convertToEntity(CarDto dto) {
        return modelMapper.map(dto, Car.class);
    }

    @GetMapping
    public ResponseEntity<Collection<CarDto>> getCars() {
        List<Car> allCars = carRepository.findAll();
        List<CarDto> result = allCars.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{carId}")
    public ResponseEntity<CarDetailsDto>
    getCarById(@PathVariable Long carId) {
        Optional<Car> car = carRepository.findById(carId);
        if(car.isPresent()) {
            CarDetailsDto carDetailsDto = convertToDetailsDto(car.get());
            return new ResponseEntity<>(carDetailsDto,
                    HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null,
                    HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping
    public ResponseEntity saveNewCar(@Valid @RequestBody CarDto car) {
        Car entity = convertToEntity(car);
        carRepository.save(entity);
        HttpHeaders headers = new HttpHeaders();
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(entity.getId())
                .toUri();
        headers.add("Location", location.toString());
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @PutMapping("/{carId}")
    public ResponseEntity updateCar(@PathVariable Long carId,@Valid @RequestBody CarDto carDto) {
        Optional<Car> currentCar =
                carRepository.findById(carId);
        if(currentCar.isPresent()) {
            carDto.setId(carId);
            Car entity = convertToEntity(carDto);
            carRepository.save(entity);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{carId}")
    public ResponseEntity deleteCar(@PathVariable Long carId)
    {
        Optional<Car> currentCar = carRepository.findById(carId);
        if(currentCar.isPresent()){
            carRepository.deleteById(carId);
            return new ResponseEntity(HttpStatus.OK);
        }else {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
    }

}
