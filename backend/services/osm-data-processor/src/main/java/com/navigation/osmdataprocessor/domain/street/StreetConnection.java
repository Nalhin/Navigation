package com.navigation.osmdataprocessor.domain.street;

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
}
