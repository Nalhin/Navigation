package com.navigation.parser.elements;

import java.util.Map;
import java.util.Objects;

public class Node {
  private final String id;
  private final String latitude;
  private final String longitude;
  private final Map<String, String> tags;

  public Node(String id, String latitude, String longitude, Map<String, String> tags) {
    this.id = id;
    this.latitude = latitude;
    this.longitude = longitude;
    this.tags = tags;
  }

  public String getId() {
    return id;
  }

  public String getLatitude() {
    return latitude;
  }

  public String getLongitude() {
    return longitude;
  }

  public Map<String, String> getTags() {
    return tags;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Node node = (Node) o;
    return Objects.equals(id, node.id) && Objects.equals(latitude, node.latitude) && Objects.equals(longitude, node.longitude) && Objects.equals(tags, node.tags);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return "Node{" +
        "id='" + id + '\'' +
        ", latitude='" + latitude + '\'' +
        ", longitude='" + longitude + '\'' +
        ", tags=" + tags +
        '}';
  }
}
