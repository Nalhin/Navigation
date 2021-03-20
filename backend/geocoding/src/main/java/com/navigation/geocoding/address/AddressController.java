package com.navigation.geocoding.address;

import com.navigation.parser.exporter.OSMExporterInMemory;
import com.navigation.parser.loader.OSMLoader;
import com.navigation.parser.loader.specification.OSMAddressDataSpecification;
import com.navigation.parser.provider.OSMProviderFile;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
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
    System.out.println(query);
    return ResponseEntity.ok(addressRepository.searchByAddress(query));
  }

  @PostConstruct
  public void init() throws IOException, XMLStreamException {

    var provider = new OSMProviderFile("/home/nalhin/Desktop/Navigation/osm-data/map-data.osm");
    var exporter = new OSMExporterInMemory();
    new OSMLoader(provider, exporter, new OSMAddressDataSpecification()).loadOSM();
    var data = exporter.getExportedData();

    for (var node : data.getNodes().values()) {
      var address = new Address();
      var tags = node.getTags();
      address.setCity(tags.get("addr:city"));
      address.setHouseNumber(tags.get("addr:housenumber"));
      address.setId(node.getId());
      address.setLatitude(node.getLatitude());
      address.setLongitude(node.getLongitude());
      address.setStreet(tags.get("addr:street"));
      address.setPostCode(tags.get("addr:postcode"));
      addressRepository.save(address);
    }
  }
}
