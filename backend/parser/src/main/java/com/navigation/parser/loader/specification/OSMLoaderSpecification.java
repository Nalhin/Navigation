package com.navigation.parser.loader.specification;

import com.navigation.parser.elements.Bounds;
import com.navigation.parser.elements.Node;
import com.navigation.parser.elements.Relation;
import com.navigation.parser.elements.Way;
import com.navigation.parser.loader.elements.Elements;

import java.util.List;

public interface OSMLoaderSpecification {

  List<Elements> DEFAULT_ORDER = List.of(Elements.METADATA, Elements.BOUNDS, Elements.NODE, Elements.WAY, Elements.RELATION);

  boolean isSatisfiedBy(Way way);

  boolean isSatisfiedBy(Node node);

  boolean isSatisfiedBy(Bounds bounds);

  boolean isSatisfiedBy(Relation relation);

  default List<Elements> getReadOrder() {
    return DEFAULT_ORDER;
  }
}
