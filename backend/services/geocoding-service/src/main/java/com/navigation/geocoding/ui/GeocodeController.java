package com.navigation.geocoding.ui;

import com.navigation.geocoding.ui.dto.response.AddressResponseDto;
import com.navigation.geocoding.application.FindMatchingAddressesUseCase;
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

  private final FindMatchingAddressesUseCase findMatchingAddressesUseCase;
  private final GeocodeApiMapper apiMapper;

  public GeocodeController(
      FindMatchingAddressesUseCase findMatchingAddressesUseCase, GeocodeApiMapper mapper) {
    this.findMatchingAddressesUseCase = findMatchingAddressesUseCase;
    this.apiMapper = mapper;
  }

  @Operation(
      tags = "geocode",
      description =
          "Find coordinates with address details that best match provided address query string")
  @ApiResponse(responseCode = "200", description = "Success")
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<AddressResponseDto>> findMatchingAddresses(
      @Param("address") String address) {
    return ResponseEntity.ok(
        findMatchingAddressesUseCase.findMatchingAddresses(address).stream()
            .map(apiMapper::toDto)
            .collect(Collectors.toList()));
  }
}
