package com.navigation.pathfindingapi.domain;

public class MapConnection {

   private final long fromId;
   private final long toId;
   private final int maxSpeed;

    public MapConnection(long fromId, long toId, int maxSpeed) {
        this.fromId = fromId;
        this.toId = toId;
        this.maxSpeed = maxSpeed;
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
