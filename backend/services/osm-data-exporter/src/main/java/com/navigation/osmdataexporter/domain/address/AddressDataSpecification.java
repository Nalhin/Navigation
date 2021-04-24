package com.navigation.osmdataexporter.domain.address;

import com.navigation.parser.elements.Bounds;
import com.navigation.parser.elements.Node;
import com.navigation.parser.elements.Relation;
import com.navigation.parser.elements.Way;
import com.navigation.parser.loader.elements.Elements;
import com.navigation.parser.loader.specification.OSMLoaderSpecification;

import java.util.List;
import java.util.Set;

public class AddressDataSpecification implements OSMLoaderSpecification {

  private static final Set<String> REQUIRED_TAGS = Set.of("addr:housenumber", "addr:street");

  @Override
  public boolean isSatisfiedBy(Way way) {
    return false;
  }

  @Override
  public boolean isSatisfiedBy(Node node) {
    return node.containsAllTagsIn(REQUIRED_TAGS);
  }

  @Override
  public boolean isSatisfiedBy(Bounds bounds) {
    return false;
  }

  @Override
  public boolean isSatisfiedBy(Relation relation) {
    return false;
  }

  @Override
  public List<Elements> getReadOrder() {
    return List.of(Elements.NODE);
  }
}
