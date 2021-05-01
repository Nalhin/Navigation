package com.navigation.geocodingapi.api;

import com.navigation.geocodingapi.api.response.AddressResponseDto;
import com.navigation.geocodingapi.api.response.LocationResponseDto;
import com.navigation.geocodingapi.domain.Address;
import org.springframework.stereotype.Component;

@Component
class ApiMapper {
  public AddressResponseDto fromEntity(Address entity) {
    var dto = new AddressResponseDto();
    dto.setCity(entity.getCity());
    dto.setCountry(entity.getCountry());
    dto.setId(entity.getId());
    dto.setLocation(
        new LocationResponseDto(
            entity.getLocation().getLatitude(), entity.getLocation().getLongitude()));
    dto.setHouseNumber(entity.getHouseNumber());
    dto.setStreet(entity.getStreet());
    dto.setPostCode(entity.getPostCode());
    return dto;
  }
}
