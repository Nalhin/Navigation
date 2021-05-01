package com.navigation.geocodingapi.infrastructure.database;

import com.navigation.geocodingapi.domain.Address;
import com.navigation.geocodingapi.domain.AddressBuilder;
import com.navigation.geocodingapi.domain.Location;
import org.springframework.stereotype.Component;

@Component
class DatabaseMapper {

  Address toDomain(AddressEntity entity) {
    return new AddressBuilder()
        .setCity(entity.getCity())
        .setHouseNumber(entity.getHouseNumber())
        .setId(entity.getId())
        .setStreet(entity.getStreet())
        .setCountry(entity.getCountry())
        .setLocation(new Location(entity.getLocation().getY(), entity.getLocation().getX()))
        .setPostCode(entity.getPostCode())
        .createAddress();
  }
}
