package com.navigation.parser.loader.specification;

import com.navigation.parser.elements.Bounds;
import com.navigation.parser.elements.Metadata;
import com.navigation.parser.elements.Node;
import com.navigation.parser.elements.Relation;
import com.navigation.parser.elements.Way;

public class OSMLoadAllSpecification implements OSMLoaderSpecification {
  @Override
  public boolean accept(Way way) {
    return true;
  }

  @Override
  public boolean accept(Node node) {
    return true;
  }

  @Override
  public boolean accept(Bounds bounds) {
    return true;
  }

  @Override
  public boolean accept(Metadata bounds) {
    return true;
  }

  @Override
  public boolean accept(Relation relation) {
    return true;
  }
}
