package com.navigation.parser.loader.specification;

import com.navigation.parser.elements.Bounds;
import com.navigation.parser.elements.Node;
import com.navigation.parser.elements.Relation;
import com.navigation.parser.elements.Way;

import java.util.Set;

public class OSMAddressDataSpecification implements OSMLoaderSpecification {

  private final static Set<String> addressTags =
      Set.of("addr:city", "addr:housenumber", "addr:postcode", "addr:street");

  @Override
  public boolean isSatisfiedBy(Way way) {
    return false;
  }

  @Override
  public boolean isSatisfiedBy(Node node) {
    for (String tag : addressTags) {
      if (node.getTags().containsKey(tag)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean isSatisfiedBy(Bounds bounds) {
    return false;
  }

  @Override
  public boolean isSatisfiedBy(Relation relation) {
    return false;
  }
}
