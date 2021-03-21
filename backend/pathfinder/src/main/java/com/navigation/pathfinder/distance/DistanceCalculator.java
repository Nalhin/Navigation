package com.navigation.pathfinder.distance;

public interface DistanceCalculator {

  double calculateDistance(double startLat, double startLong, double endLat, double endLong);
}
