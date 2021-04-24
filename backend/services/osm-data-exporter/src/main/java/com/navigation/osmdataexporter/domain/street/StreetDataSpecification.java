package com.navigation.osmdataexporter.domain.street;

import com.navigation.parser.elements.Bounds;
import com.navigation.parser.elements.Node;
import com.navigation.parser.elements.Relation;
import com.navigation.parser.elements.Way;
import com.navigation.parser.loader.elements.Elements;
import com.navigation.parser.loader.specification.OSMLoaderSpecification;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StreetDataSpecification implements OSMLoaderSpecification {

  private static final String HIGHWAY_TAG = "highway";
  private static final Set<String> HIGHWAY_TAG_VALUES =
      Set.of(
          "road",
          "service",
          "living_street",
          "tertiary_link",
          "secondary_link",
          "primary_link",
          "trunk_link",
          "motorway_link",
          "residential",
          "unclassified",
          "tertiary",
          "secondary",
          "primary",
          "trunk",
          "motorway");

  private final Set<Long> allowList = new HashSet<>();

  @Override
  public boolean isSatisfiedBy(Way way) {
    if (!way.containsTagWithAnyValueIn(HIGHWAY_TAG, HIGHWAY_TAG_VALUES)) {
      return false;
    }
    allowList.addAll(way.getNodeReferences());
    return true;
  }

  @Override
  public boolean isSatisfiedBy(Node node) {
    return allowList.contains(node.getId());
  }

  @Override
  public boolean isSatisfiedBy(Bounds bounds) {
    return true;
  }

  @Override
  public boolean isSatisfiedBy(Relation relation) {
    return false;
  }

  @Override
  public List<Elements> getReadOrder() {
    return List.of(Elements.WAY, Elements.NODE);
  }
}
