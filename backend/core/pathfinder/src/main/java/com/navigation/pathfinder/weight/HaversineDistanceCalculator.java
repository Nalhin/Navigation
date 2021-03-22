package com.navigation.pathfinder.weight;

import com.navigation.pathfinder.graph.Coordinates;

class HaversineDistanceCalculator {

  private static final int EARTH_RADIUS = 6371;

  double calculateDistance(Coordinates startCoors, Coordinates endCoords) {
    double startLat = startCoors.getLatitude();
    double startLong = startCoors.getLongitude();

    double endLat = endCoords.getLatitude();
    double endLong = endCoords.getLongitude();

    double dLat = Math.toRadians((endLat - startLat));
    double dLong = Math.toRadians((endLong - startLong));

    startLat = Math.toRadians(startLat);
    endLat = Math.toRadians(endLat);

    double a = haversin(dLat) + Math.cos(startLat) * Math.cos(endLat) * haversin(dLong);
    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

    return EARTH_RADIUS * c;
  }

  private double haversin(double val) {
    return Math.pow(Math.sin(val / 2), 2);
  }
}
