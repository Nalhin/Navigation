package com.navigation.parser.elements;

import java.util.List;
import java.util.Objects;

public class Relation {

  private final String id;
  private final List<Member> members;
  private final List<Tag> tags;

  public Relation(String id, List<Member> members, List<Tag> tags) {
    this.id = id;
    this.members = members;
    this.tags = tags;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Relation relation = (Relation) o;
    return Objects.equals(id, relation.id) && Objects.equals(members, relation.members) && Objects.equals(tags, relation.tags);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  public String getId() {
    return id;
  }

  public List<Member> getMembers() {
    return members;
  }

  public List<Tag> getTags() {
    return tags;
  }

  @Override
  public String toString() {
    return "Relation{" +
        "id='" + id + '\'' +
        ", members=" + members +
        ", tags=" + tags +
        '}';
  }
}
