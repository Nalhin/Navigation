package com.navigation.osmdataprocessor.domain.street;

import java.util.Objects;

public class StreetConnection {
  private final long fromId;
  private final long toId;
  private final int maxSpeed;

  public StreetConnection(long fromId, long toId, int maxSpeed) {
    this.maxSpeed = maxSpeed;
    this.fromId = fromId;
    this.toId = toId;
  }

  public long getFromId() {
    return fromId;
  }

  public long getToId() {
    return toId;
  }

  public int getMaxSpeed() {
    return maxSpeed;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    StreetConnection that = (StreetConnection) o;
    return fromId == that.fromId && toId == that.toId && maxSpeed == that.maxSpeed;
  }

  @Override
  public int hashCode() {
    return Objects.hash(fromId, toId, maxSpeed);
  }
}
