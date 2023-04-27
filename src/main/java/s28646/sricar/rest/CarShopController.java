package s28646.sricar.rest;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import s28646.sricar.dto.CarDto;
import s28646.sricar.dto.CarShopDetailsDto;
import s28646.sricar.dto.CarShopDto;
import s28646.sricar.dto.mapper.CarShopDtoMapper;
import s28646.sricar.model.CarShop;
import s28646.sricar.repo.CarRepository;
import s28646.sricar.repo.CarShopRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/carshops")
public class CarShopController {

    private final CarRepository carRepository;
    private final CarShopRepository carShopRepository;
    private final ModelMapper modelMapper;
    private final CarShopDtoMapper carShopDtoMapper;


    @GetMapping
    public ResponseEntity<Collection<CarShopDto>> getCarShops() {
        List<CarShop> allCarShopEntities = carShopRepository.findAll();
        List<CarShopDto> result = allCarShopEntities.stream()
                .map(carShopDtoMapper::convertToDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{carShopId}")
    public ResponseEntity<CarShopDetailsDto> getCarShopById(@PathVariable Long carShopId) {
        Optional<CarShop> carShop = carShopRepository.findById(carShopId);
        if(carShop.isPresent()) {
            CarShopDetailsDto carShopDetailsDto = carShopDtoMapper.convertToDetailsDto(carShop.get());
            return new ResponseEntity<>(carShopDetailsDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
//    @PostMapping
//    public ResponseEntity saveNewCar(@RequestBody CarDto
//                                             car) {
//        Car entity = convertToEntity(car);
//        carRepository.save(entity);
//        HttpHeaders headers = new HttpHeaders();
//        URI location = ServletUriComponentsBuilder
//                .fromCurrentRequest()
//                .path("/{id}")
//                .buildAndExpand(entity.getId())
//                .toUri();
//        headers.add("Location", location.toString());
//        return new ResponseEntity(headers, HttpStatus.CREATED);
//    }
//
//    @PutMapping("/{carId}")
//    public ResponseEntity updateCar(@PathVariable Long
//                                            carId, @RequestBody CarDto carDto) {
//        Optional<Car> currentEmp =
//                carRepository.findById(carId);
//        if(currentEmp.isPresent()) {
//            carDto.setId(carId);
//            Car entity = convertToEntity(carDto);
//            carRepository.save(entity);
//            return new ResponseEntity(HttpStatus.NO_CONTENT);
//        } else {
//            return new ResponseEntity(HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @DeleteMapping("/{carId}")
//    public ResponseEntity deleteCar(@PathVariable Long carId)
//    {
//        carRepository.deleteById(carId);
//        return new ResponseEntity(HttpStatus.NO_CONTENT);
//    }
//
}

