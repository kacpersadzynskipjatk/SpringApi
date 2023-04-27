package s28646.sricar.dto.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import s28646.sricar.dto.CarDto;
import s28646.sricar.dto.CarShopDetailsDto;
import s28646.sricar.dto.CarShopDto;
import s28646.sricar.model.CarShop;

@Component
@RequiredArgsConstructor
public class CarShopDtoMapper {
    private final ModelMapper modelMapper;

    public CarShopDetailsDto convertToDetailsDto(CarShop e) {
        return modelMapper.map(e, CarShopDetailsDto.class);
    }

    public CarShopDto convertToDto(CarShop e) {
        return modelMapper.map(e, CarShopDto.class);
    }

    private CarShop convertToEntity(CarDto dto) {
        return modelMapper.map(dto, CarShop.class);
    }
}
