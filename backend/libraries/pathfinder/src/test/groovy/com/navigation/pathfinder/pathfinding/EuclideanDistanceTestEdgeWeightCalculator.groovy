package com.navigation.pathfinder.pathfinding

import com.navigation.pathfinder.graph.Edge
import com.navigation.pathfinder.weight.EdgeWeightCalculator

class EuclideanDistanceTestEdgeWeightCalculator implements
    EdgeWeightCalculator {

  @Override
  double calculateWeight(Edge edge) {
    def fromCoords = edge.from.coordinates
    def toCoords = edge.to.coordinates
    return Math.sqrt(Math.pow(fromCoords.latitude - fromCoords.longitude, 2) +
        Math.pow(toCoords.longitude - toCoords.latitude, 2))
  }
}