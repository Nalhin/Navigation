package com.navigation.pathfinder.weight;

import com.navigation.pathfinder.graph.Edge;

public interface EdgeWeightCalculator {

  double calculateWeight(Edge edge);

}
