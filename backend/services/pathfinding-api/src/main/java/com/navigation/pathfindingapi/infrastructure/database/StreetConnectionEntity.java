package com.navigation.pathfindingapi.infrastructure.database;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("streetConnections")
class StreetConnectionEntity {

  @Id private String id;

  @Indexed private long fromId;
  @Indexed private long toId;

  private int maxSpeed;

  public StreetConnectionEntity(String id, long fromId, long toId, int maxSpeed) {
    this.id = id;
    this.fromId = fromId;
    this.toId = toId;
    this.maxSpeed = maxSpeed;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
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

  public int getMaxSpeed() {
    return maxSpeed;
  }

  public void setMaxSpeed(int maxSpeed) {
    this.maxSpeed = maxSpeed;
  }
}
