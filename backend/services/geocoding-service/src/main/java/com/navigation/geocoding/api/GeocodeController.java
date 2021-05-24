package com.navigation.geocoding.api;

import com.navigation.geocoding.api.response.AddressResponseDto;
import com.navigation.geocoding.domain.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "geocode")
@Controller
@RequestMapping("/api/v1/geocode")
class GeocodeController {

  private final AddressService addressService;
  private final ApiMapper apiMapper;

  public GeocodeController(AddressService addressService, ApiMapper mapper) {
    this.addressService = addressService;
    this.apiMapper = mapper;
  }

  @Operation(
      tags = "geocode",
      description = "Find addresses that best match provided address")
  @ApiResponse(responseCode = "200", description = "Success")
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<AddressResponseDto>> findAddressesByText(
      @Param("address") String address) {
    return ResponseEntity.ok(
        addressService.findAllByAddress(address).stream()
            .map(apiMapper::toDto)
            .collect(Collectors.toList()));
  }
}