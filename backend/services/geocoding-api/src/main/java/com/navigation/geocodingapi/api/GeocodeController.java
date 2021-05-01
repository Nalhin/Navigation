package com.navigation.geocodingapi.api;

import com.navigation.geocodingapi.api.response.AddressResponseDto;
import com.navigation.geocodingapi.domain.AddressService;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api/v1/geocode")
class GeocodeController {

  private final AddressService addressService;
  private final ApiMapper apiMapper;

  public GeocodeController(AddressService addressService, ApiMapper mapper) {
    this.addressService = addressService;
    this.apiMapper = mapper;
  }

  @GetMapping
  public ResponseEntity<List<AddressResponseDto>> getAddressByText(
      @Param("address") String address) {
    return ResponseEntity.ok(
        addressService.findAllByAddress(address).stream()
            .map(apiMapper::fromEntity)
            .collect(Collectors.toList()));
  }
}
