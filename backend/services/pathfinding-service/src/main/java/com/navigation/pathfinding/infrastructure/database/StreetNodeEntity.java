package com.navigation.pathfinding.infrastructure.database;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("streetNodes")
class StreetNodeEntity {
  @Id private long id;

  @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
  private GeoJsonPoint location;

  public StreetNodeEntity(long id, GeoJsonPoint location) {
    this.id = id;
    this.location = location;
  }

  public long getId() {
    return id;
  }

  public GeoJsonPoint getLocation() {
    return location;
  }

  public void setId(long id) {
    this.id = id;
  }

  public void setLocation(GeoJsonPoint location) {
    this.location = location;
  }
}
