package com.navigation.osmdataprocessor.domain.address;

import com.navigation.parser.elements.Node;

class AddressMapper {

  Address fromNode(Node node) {
    return new Address.AddressBuilder()
        .setCity(node.getTag("addr:city"))
        .setCountry("Poland")
        .setId(node.getId())
        .setHouseNumber(node.getTag("addr:housenumber"))
        .setStreet(node.getTag("addr:street"))
        .setPostCode(node.getTag("addr:postcode"))
        .setLatitude(node.getLatitude())
        .setLongitude(node.getLongitude())
        .createAddress();
  }
}
