package com.navigation.reversegeocodingapi.infrastructure.database;

import com.navigation.reversegeocodingapi.domain.Address;
import com.navigation.reversegeocodingapi.domain.Location;
import org.springframework.stereotype.Component;

@Component
public class DatabaseMapper {

  Address toDomain(AddressEntity entity) {
    return new Address.AddressBuilder()
        .setCity(entity.getCity())
        .setHouseNumber(entity.getHouseNumber())
        .setId(entity.getId())
        .setStreet(entity.getStreet())
        .setCountry(entity.getCountry())
        .setLocation(
            new Location(
                entity.getLocation().getCoordinates().get(1),
                entity.getLocation().getCoordinates().get(0)))
        .setPostCode(entity.getPostCode())
        .createAddress();
  }
}
