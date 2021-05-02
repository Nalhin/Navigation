package com.navigation.parser.elements;

public interface ElementVisitor {
  boolean accept(Bounds bounds);

  boolean accept(Metadata bounds);

  boolean accept(Node bounds);

  boolean accept(Relation bounds);

  boolean accept(Way bounds);
}
