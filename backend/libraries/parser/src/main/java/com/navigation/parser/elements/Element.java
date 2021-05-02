package com.navigation.parser.elements;

public interface Element {
  boolean accept(ElementVisitor visitor);
}
