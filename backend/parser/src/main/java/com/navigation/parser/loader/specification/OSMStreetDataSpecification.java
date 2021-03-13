package com.navigation.parser.loader.specification;

import com.navigation.parser.elements.Bounds;
import com.navigation.parser.elements.Node;
import com.navigation.parser.elements.Relation;
import com.navigation.parser.elements.Way;
import com.navigation.parser.loader.elements.Elements;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class OSMStreetDataSpecification implements OSMLoaderSpecification {

  private final static String HIGHWAY_KEY = "highway";
  private final static Set<String> highwayTags =
      Set.of("road", "service", "living_street",
          "tertiary_link", "secondary_link", "primary_link",
          "trunk_link", "motorway_link", "residential",
          "unclassified", "tertiary", "secondary",
          "primary", "trunk", "motorway"
      );

  private final TrieAllowList allowList = new TrieAllowList();

  @Override
  public boolean isSatisfiedBy(Way way) {
    Map<String, String> tags = way.getTags();

    if (!tags.containsKey(HIGHWAY_KEY)) {
      return false;
    }

    if (!highwayTags.contains(tags.get(HIGHWAY_KEY))) {
      return false;
    }

    for (String ref : way.getNodeReferences()) {
      allowList.allow(ref);
    }
    return true;
  }

  @Override
  public boolean isSatisfiedBy(Node node) {
    return allowList.isAllowed(node.getId());
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
    return List.of(Elements.METADATA, Elements.BOUNDS, Elements.WAY, Elements.RELATION, Elements.NODE);
  }
}
