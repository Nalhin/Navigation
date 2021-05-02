package com.navigation.parser.elements;

import java.util.Objects;

public final class Member {

  private final String type;
  private final long ref;
  private final String role;

  public Member(String type, long ref, String role) {
    this.type = type;
    this.ref = ref;
    this.role = role;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Member member = (Member) o;
    return Objects.equals(type, member.type) && Objects.equals(ref, member.ref) && Objects.equals(role, member.role);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, ref, role);
  }

  @Override
  public String toString() {
    return "Member{" +
        "type='" + type + '\'' +
        ", ref='" + ref + '\'' +
        ", role='" + role + '\'' +
        '}';
  }
}
