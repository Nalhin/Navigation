package com.navigation.parser.elements;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Way extends TaggedElement implements Element {

  private final List<Long> nodeReferences;

  public Way(long id, Map<String, String> tags, List<Long> nodeReferences) {
    super(id, tags);
    this.nodeReferences = nodeReferences;
  }

  @Override
  public boolean accept(ElementVisitor visitor) {
    return visitor.accept(this);
  }

  public List<Long> getNodeReferences() {
    return nodeReferences;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    Way way = (Way) o;
    return Objects.equals(nodeReferences, way.nodeReferences);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode());
  }

  @Override
  public String toString() {
    return "Way{" + "nodeReferences=" + nodeReferences + "} " + super.toString();
  }
}
