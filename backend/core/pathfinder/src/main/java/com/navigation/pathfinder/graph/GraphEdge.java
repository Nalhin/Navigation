package com.navigation.pathfinder.graph;

import com.navigation.pathfinder.distance.DistanceCalculator;

public class GraphEdge {

  private final GraphNode from;
  private final GraphNode to;
  private final DistanceCalculator distanceCalculator;

  public GraphEdge(GraphNode from, GraphNode to, DistanceCalculator distanceCalculator) {
    this.from = from;
    this.to = to;
    this.distanceCalculator = distanceCalculator;
  }

  public double getDistance() {
    double lat1 = from.getLatitude();
    double lon1 = from.getLongitude();
    double lat2 = to.getLatitude();
    double lon2 = to.getLongitude();

    return distanceCalculator.calculateDistance(lat1, lon1, lat2, lon2);
  }

  public GraphNode getFrom() {
    return from;
  }

  public GraphNode getTo() {
    return to;
  }
}
