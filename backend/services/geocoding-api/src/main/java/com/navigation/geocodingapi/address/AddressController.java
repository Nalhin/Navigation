package com.navigation.geocodingapi.address;

import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value = "/address")
public class AddressController {

  private final AddressRepository addressRepository;

  public AddressController(AddressRepository addressRepository) {
    this.addressRepository = addressRepository;
  }

  @GetMapping
  public ResponseEntity<List<Address>> getAddressByText(@Param("query") String query) {
    return ResponseEntity.ok(addressRepository.searchByAddress(query));
  }
}
