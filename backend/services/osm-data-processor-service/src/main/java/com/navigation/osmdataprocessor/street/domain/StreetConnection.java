package com.navigation.osmdataprocessor.street.domain;

import java.util.Objects;

public class StreetConnection {

  private final String id;
  private final long fromId;
  private final long toId;
  private final int maxSpeed;

  public StreetConnection(String id, long fromId, long toId, int maxSpeed) {
    this.id = id;
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

  public String getId() {
    return id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    StreetConnection that = (StreetConnection) o;
    return fromId == that.fromId
        && toId == that.toId
        && maxSpeed == that.maxSpeed
        && Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, fromId, toId, maxSpeed);
  }
}
