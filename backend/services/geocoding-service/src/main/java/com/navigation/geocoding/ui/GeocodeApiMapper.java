package com.navigation.geocoding.ui;

import com.navigation.geocoding.ui.dto.response.AddressResponseDto;
import com.navigation.geocoding.ui.dto.response.LocationResponseDto;
import com.navigation.geocoding.domain.Address;
import org.springframework.stereotype.Component;

@Component
class GeocodeApiMapper {

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
