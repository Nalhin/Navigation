package com.navigation.pathfindingapi.domain;

import com.navigation.pathfinder.graph.Coordinates;

import java.util.Objects;

public class BoundsQuery {

  private final Coordinates leftBottom;
  private final Coordinates topRight;

  public BoundsQuery(Coordinates leftBottom, Coordinates topRight) {
    this.leftBottom = leftBottom;
    this.topRight = topRight;
  }

  public Coordinates getLeftBottom() {
    return leftBottom;
  }

  public Coordinates getTopRight() {
    return topRight;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    BoundsQuery that = (BoundsQuery) o;
    return Objects.equals(leftBottom, that.leftBottom) && Objects.equals(topRight, that.topRight);
  }

  @Override
  public int hashCode() {
    return Objects.hash(leftBottom, topRight);
  }
}
