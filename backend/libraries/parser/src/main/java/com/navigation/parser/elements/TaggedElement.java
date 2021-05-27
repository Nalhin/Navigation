package com.navigation.parser.elements;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

abstract class TaggedElement {

  private final long id;
  private final Map<String, String> tags;

  protected TaggedElement(long id, Map<String, String> tags) {
    this.id = id;
    this.tags = Map.copyOf(tags);
  }

  public boolean containsTag(String tag) {
    return tags.containsKey(tag);
  }

  public boolean containsAnyTagIn(Collection<String> values) {
    for (String tag : values) {
      if (tags.containsKey(tag)) {
        return true;
      }
    }
    return false;
  }

  public boolean containsAllTagsIn(Collection<String> values) {
    for (String tag : values) {
      if (!tags.containsKey(tag)) {
        return false;
      }
    }
    return true;
  }

  public boolean containsTagWithValue(String tag, String value) {
    if (!containsTag(tag)) {
      return false;
    }
    return tags.get(tag).equals(value);
  }

  public boolean containsTagWithAnyValueIn(String tag, Collection<String> values) {
    if (!containsTag(tag)) {
      return false;
    }
    return values.contains(tags.get(tag));
  }

  public String getTag(String tagName) {
    return tags.get(tagName);
  }

  public long getId() {
    return id;
  }

  public Map<String, String> getTags() {
    return tags;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TaggedElement that = (TaggedElement) o;
    return Objects.equals(id, that.id) && Objects.equals(tags, that.tags);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return "TaggedElement{" + "id='" + id + '\'' + ", tags=" + tags + '}';
  }
}
