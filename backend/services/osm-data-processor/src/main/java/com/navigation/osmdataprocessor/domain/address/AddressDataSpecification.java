package com.navigation.osmdataprocessor.domain.address;

import com.navigation.parser.elements.Bounds;
import com.navigation.parser.elements.Metadata;
import com.navigation.parser.elements.Node;
import com.navigation.parser.elements.Relation;
import com.navigation.parser.elements.Way;
import com.navigation.parser.types.ElementTypes;
import com.navigation.parser.specification.OSMLoaderSpecification;

import java.util.List;
import java.util.Set;

class AddressDataSpecification implements OSMLoaderSpecification {

  private static final Set<String> REQUIRED_TAGS = Set.of("addr:housenumber", "addr:street");

  @Override
  public boolean accept(Way way) {
    return false;
  }

  @Override
  public boolean accept(Node node) {
    return node.containsAllTagsIn(REQUIRED_TAGS);
  }

  @Override
  public boolean accept(Bounds bounds) {
    return false;
  }

  @Override
  public boolean accept(Metadata bounds) {
    return false;
  }

  @Override
  public boolean accept(Relation relation) {
    return false;
  }

  @Override
  public List<ElementTypes> getReadOrder() {
    return List.of(ElementTypes.NODE);
  }
}
