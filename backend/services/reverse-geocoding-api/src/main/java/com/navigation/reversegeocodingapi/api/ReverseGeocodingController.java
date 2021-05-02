package com.navigation.reversegeocodingapi.api;

import com.navigation.reversegeocodingapi.api.response.AddressDto;
import com.navigation.reversegeocodingapi.domain.Location;
import com.navigation.reversegeocodingapi.domain.ReverseGeocodingService;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/api/v1/reverse-geocode")
class ReverseGeocodingController {

  private final ReverseGeocodingService service;
  private final ApiMapper mapper;

  public ReverseGeocodingController(ReverseGeocodingService service, ApiMapper mapper) {
    this.service = service;
    this.mapper = mapper;
  }

  @GetMapping
  public ResponseEntity<AddressDto> getClosestAddress(
          @RequestParam("latitude") Double latitude, @RequestParam("longitude") Double longitude) {

    return service
        .findClosestAddress(new Location(latitude, longitude))
        .map(mapper::toDto)
        .map(ResponseEntity::ok)
        .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find address"));
  }
}
