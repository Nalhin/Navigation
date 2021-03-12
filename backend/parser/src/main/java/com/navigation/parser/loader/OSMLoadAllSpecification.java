package com.navigation.parser.loader;

import com.navigation.parser.elements.Bounds;
import com.navigation.parser.elements.Node;
import com.navigation.parser.elements.Relation;
import com.navigation.parser.elements.Way;

class OSMLoadAllSpecification implements OSMLoaderSpecification {
  @Override
  public boolean isSatisfiedBy(Way way) {
    return true;
  }

  @Override
  public boolean isSatisfiedBy(Node node) {
    return true;
  }

  @Override
  public boolean isSatisfiedBy(Bounds bounds) {
    return true;
  }

  @Override
  public boolean isSatisfiedBy(Relation relation) {
    return true;
  }
}
