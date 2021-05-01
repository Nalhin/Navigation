package com.navigation.geocodingapi.api;

import com.navigation.geocodingapi.api.response.AddressResponseDto;
import com.navigation.geocodingapi.api.response.LocationResponseDto;
import com.navigation.geocodingapi.domain.Address;
import org.springframework.stereotype.Component;

@Component
class ApiMapper {

  AddressResponseDto toDto(Address domain) {
    var dto = new AddressResponseDto();
    dto.setCity(domain.getCity());
    dto.setCountry(domain.getCountry());
    dto.setId(domain.getId());
    dto.setLocation(
        new LocationResponseDto(
            domain.getLocation().getLatitude(), domain.getLocation().getLongitude()));
    dto.setHouseNumber(domain.getHouseNumber());
    dto.setStreet(domain.getStreet());
    dto.setPostCode(domain.getPostCode());
    return dto;
  }
}
