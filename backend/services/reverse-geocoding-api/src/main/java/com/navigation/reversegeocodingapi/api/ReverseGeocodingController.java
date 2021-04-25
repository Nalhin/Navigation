package com.navigation.reversegeocodingapi.api;

import com.navigation.reversegeocodingapi.infrastructure.database.MongoAddressRepository;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/api/v1/address")
public class ReverseGeocodingController {

  private final MongoAddressRepository repository;

  public ReverseGeocodingController(MongoAddressRepository repository) {
    this.repository = repository;
  }

  @GetMapping("/closest")
  public ResponseEntity<AddressDto> getClosestAddress(
      @Param("latitude") Double latitude, @Param("longitude") Double longitude) {
    var closestAddress =
        repository.findTop1ByLocationNear(
            new GeoJsonPoint(longitude, latitude), new Distance(0.2, Metrics.KILOMETERS));

    return closestAddress
        .map(AddressDto::fromEntity)
        .map(ResponseEntity::ok)
        .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find address"));
  }
}
