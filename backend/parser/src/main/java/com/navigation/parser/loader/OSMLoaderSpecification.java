package com.navigation.parser.loader;

import com.navigation.parser.elements.Bounds;
import com.navigation.parser.elements.Node;
import com.navigation.parser.elements.Relation;
import com.navigation.parser.elements.Way;

public interface OSMLoaderSpecification {

  boolean isSatisfiedBy(Way way);

  boolean isSatisfiedBy(Node node);

  boolean isSatisfiedBy(Bounds bounds);

  boolean isSatisfiedBy(Relation relation);
}
