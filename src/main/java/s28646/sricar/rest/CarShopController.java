package s28646.sricar.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import s28646.sricar.dto.CarDto;
import s28646.sricar.dto.CarShopDetailsDto;
import s28646.sricar.dto.CarShopDto;
import s28646.sricar.dto.ErrorMessage;
import s28646.sricar.dto.mapper.CarShopDtoMapper;
import s28646.sricar.model.Car;
import s28646.sricar.model.CarShop;
import s28646.sricar.repo.CarRepository;
import s28646.sricar.repo.CarShopRepository;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/carshops")
public class CarShopController {

    private final CarRepository carRepository;
    private final CarShopRepository carShopRepository;
    private final ModelMapper modelMapper;
    private final CarShopDtoMapper carShopDtoMapper;


    @GetMapping(produces = {"application/hal+json"})
    public ResponseEntity<CollectionModel<CarShopDto>> getCarShops() {
        List<CarShop> allCarShopEntities = carShopRepository.findAll();
        List<CarShopDto> result = allCarShopEntities.stream()
                .map(carShopDtoMapper::convertToDto)
                .collect(Collectors.toList());
        for (CarShopDto dto : result){
            dto.add(createCarShopSelfLink(dto.getId()));
            dto.add(createCarShopCarsLink(dto.getId()));
        }
        Link linkSelf = linkTo(methodOn(CarShopController.class).getCarShops()).withSelfRel();
        CollectionModel<CarShopDto> res = CollectionModel.of(result, linkSelf);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping(value = "/{carShopId}", produces = {"application/hal+json"})
    public ResponseEntity<CarShopDetailsDto> getCarShopById(@PathVariable Long carShopId) {
        Optional<CarShop> carShop = carShopRepository.findById(carShopId);
        if(carShop.isPresent()) {
            CarShopDetailsDto carShopDetailsDto = carShopDtoMapper.convertToDetailsDto(carShop.get());
            carShopDetailsDto.add(createCarShopSelfLink(carShopId));
            return new ResponseEntity<>(carShopDetailsDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/{carShopId}/cars")
    public ResponseEntity<Collection<CarDto>> getCarsByCarShopId(@PathVariable Long carShopId) {
        List<Car> allCars = carRepository.findCarsByCarShopId(carShopId);
        List<CarDto> result = allCars.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity saveNewCarShop(@Valid @RequestBody CarShopDto carShop) {
        CarShop entity = carShopDtoMapper.convertToEntity(carShop);
        carShopRepository.save(entity);
        HttpHeaders headers = new HttpHeaders();
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(entity.getId())
                .toUri();
        headers.add("Location", location.toString());
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @PutMapping("/{carShopId}")
    public ResponseEntity updateCarShop(@PathVariable Long carShopId,@Valid @RequestBody CarShopDto carShopDto) {
        Optional<CarShop> currentCarShop = carShopRepository.findById(carShopId);
        if(currentCarShop.isPresent()) {
            carShopDto.setId(carShopId);
            CarShop entity = carShopDtoMapper.convertToEntity(carShopDto);
            carShopRepository.save(entity);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    //dodanie asocjacji
    @PostMapping("/{carShopId}/cars/")
    public ResponseEntity connectCarToCarShop(@Valid @RequestBody CarDto carDto, @PathVariable Long carShopId) {
        Optional<CarShop> currentCarShop = carShopRepository.findById(carShopId);
        if(currentCarShop.isPresent()) {
            Car entity = convertToEntity(carDto);
            entity.setCarShop(currentCarShop.get());
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
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    //usunięcie asocjacji
    @DeleteMapping("/{carShopId}/cars/{carId}")
    public ResponseEntity deleteCarformCarShop(@PathVariable Long carId)
    {
        Optional<Car> currentCar = carRepository.findById(carId);
        if(currentCar.isPresent()){
            carRepository.deleteById(carId);
            return new ResponseEntity(HttpStatus.OK);
        }else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/{carShopId}")
    public Object deleteCarShop(@Valid @PathVariable Long carShopId)
    {
        Optional<CarShop> currentCarShop = carShopRepository.findById(carShopId);

        if(currentCarShop.isPresent()){
            CarShop currShop = currentCarShop.get();
            if (currShop.getCars().isEmpty()){
                carShopRepository.deleteById(carShopId);
                return new ResponseEntity(HttpStatus.OK);
            }else {
                //return new ResponseEntity(HttpStatus.BAD_REQUEST);
                Map<String, List<String>> errors = new HashMap<String, List<String>>() {};
                List<String> message = new LinkedList<>() {};
                message.add("Cannot delete car shop it has reference to its cars");
                errors.put("Message", message);
                return new ErrorMessage(HttpStatus.BAD_REQUEST, LocalDateTime.now(), errors, "");
            }
        }else {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
    }
    private CarDto convertToDto(Car c){
        return modelMapper.map(c, CarDto.class);
    }

    private Car convertToEntity(CarDto dto){
        return modelMapper.map(dto, Car.class);
    }

    private Link createCarShopSelfLink(Long carShopId){
        return linkTo(methodOn(CarShopController.class).getCarShopById(carShopId)).withSelfRel();
    }

    private Link createCarShopCarsLink(Long carShopId){
        return linkTo(methodOn(CarShopController.class).getCarsByCarShopId(carShopId)).withSelfRel();
    }
}

