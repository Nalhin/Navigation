package com.navigation.reversegeocodingapi.ui;

import com.navigation.reversegeocodingapi.ui.response.AddressDto;
import com.navigation.reversegeocodingapi.domain.Location;
import com.navigation.reversegeocodingapi.application.FindClosestAddressUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

@Tag(name = "reverse-geocode")
@Controller
@RequestMapping("/api/v1/reverse-geocode")
class ReverseGeocodingController {

  private final FindClosestAddressUseCase service;
  private final ReverseGeocodingApiMapper mapper;

  public ReverseGeocodingController(FindClosestAddressUseCase service, ReverseGeocodingApiMapper mapper) {
    this.service = service;
    this.mapper = mapper;
  }

  @Operation(
      tags = "reverse-geocode",
      description = "Find address closest to the given coordinates")
  @ApiResponse(responseCode = "200", description = "Success")
  @ApiResponse(responseCode = "404", description = "Address not found")
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
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
