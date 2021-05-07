package com.navigation.reversegeocodingapi.api;

import com.navigation.reversegeocodingapi.api.response.AddressDto;
import com.navigation.reversegeocodingapi.api.response.LocationDto;
import com.navigation.reversegeocodingapi.domain.Address;
import com.navigation.reversegeocodingapi.domain.Location;
import org.springframework.stereotype.Component;

@Component
class ReverseGeocodingApiMapper {
  public AddressDto toDto(Address entity) {
    var dto = new AddressDto();
    dto.setCity(entity.getCity());
    dto.setCountry(entity.getCountry());
    dto.setId(entity.getId());
    dto.setLocation(toDto(entity.getLocation()));
    dto.setHouseNumber(entity.getHouseNumber());
    dto.setStreet(entity.getStreet());
    dto.setPostCode(entity.getPostCode());
    return dto;
  }

  public LocationDto toDto(Location location) {
    return new LocationDto(location.getLatitude(), location.getLongitude());
  }
}
