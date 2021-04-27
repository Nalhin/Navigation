package com.navigation.geocodingapi.api;

import com.navigation.geocodingapi.infrastructure.AddressEntity;
import com.navigation.geocodingapi.infrastructure.AddressRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/v1/geocode")
public class GeocodeController {

  private final AddressRepository addressRepository;

  public GeocodeController(AddressRepository addressRepository) {
    this.addressRepository = addressRepository;
  }

  @GetMapping
  public ResponseEntity<List<AddressEntity>> getAddressByText(@Param("address") String address) {
    return ResponseEntity.ok(addressRepository.searchByAddress(address));
  }
}
