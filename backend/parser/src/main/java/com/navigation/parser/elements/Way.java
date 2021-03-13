package com.navigation.parser.elements;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Way {

  private final String id;
  private final List<String> nodeReferences;
  private final Map<String, String> tags;

  public Way(String id, List<String> nodeReferences, Map<String, String> tags) {
    this.id = id;
    this.nodeReferences = nodeReferences;
    this.tags = tags;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Way way = (Way) o;
    return Objects.equals(id, way.id) && Objects.equals(nodeReferences, way.nodeReferences) && Objects.equals(tags, way.tags);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  public String getId() {
    return id;
  }

  public Map<String, String> getTags() {
    return tags;
  }

  public List<String> getNodeReferences() {
    return nodeReferences;
  }

  @Override
  public String toString() {
    return "Way{" +
        "id='" + id + '\'' +
        ", nodeReferences=" + nodeReferences +
        ", tags=" + tags +
        '}';
  }
}
