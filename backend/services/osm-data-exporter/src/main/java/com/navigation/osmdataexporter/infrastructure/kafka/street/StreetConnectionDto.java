package com.navigation.osmdataexporter.infrastructure.kafka.street;

public class StreetConnectionDto {
  private long fromId;
  private long toId;
  private int maxSpeedInKmPerHour;

  public StreetConnectionDto(long fromId, long toId, int maxSpeedInKmPerHour) {
    this.maxSpeedInKmPerHour = maxSpeedInKmPerHour;
    this.fromId = fromId;
    this.toId = toId;
  }

  public long getFromId() {
    return fromId;
  }

  public void setFromId(long fromId) {
    this.fromId = fromId;
  }

  public long getToId() {
    return toId;
  }

  public void setToId(long toId) {
    this.toId = toId;
  }

  public int getMaxSpeedInKmPerHour() {
    return maxSpeedInKmPerHour;
  }

  public void setMaxSpeedInKmPerHour(int maxSpeedInKmPerHour) {
    this.maxSpeedInKmPerHour = maxSpeedInKmPerHour;
  }
}
