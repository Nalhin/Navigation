package com.navigation.parser.loader.specification;

import com.navigation.parser.elements.ElementVisitor;
import com.navigation.parser.loader.elements.ElementTypes;

import java.util.List;

public interface OSMLoaderSpecification extends ElementVisitor {

  List<ElementTypes> DEFAULT_ORDER = List.of(ElementTypes.METADATA, ElementTypes.BOUNDS, ElementTypes.NODE, ElementTypes.WAY, ElementTypes.RELATION);

  static int getDefaultPosition(ElementTypes element) {
    return DEFAULT_ORDER.indexOf(element);
  }

  default List<ElementTypes> getReadOrder() {
    return DEFAULT_ORDER;
  }
}
