package com.navigation.parser.elements;

public interface ElementVisitor {
  boolean accept(Bounds bounds);

  boolean accept(Metadata metadata);

  boolean accept(Node node);

  boolean accept(Relation relation);

  boolean accept(Way way);
}
