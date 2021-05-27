package com.navigation.parser.elements;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class Relation extends TaggedElement implements Element{

  private final List<Member> members;

  public Relation(long id, Map<String, String> tags, List<Member> members) {
    super(id, tags);
    this.members = List.copyOf(members);
  }

  @Override
  public boolean accept(ElementVisitor visitor) {
    return visitor.accept(this);
  }

  public List<Member> getMembers() {
    return members;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    Relation relation = (Relation) o;
    return Objects.equals(members, relation.members);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), members);
  }

  @Override
  public String toString() {
    return "Relation{" +
        "members=" + members +
        "} " + super.toString();
  }
}
