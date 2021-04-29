package com.navigation.pathfinder.convexhull;

import com.navigation.pathfinder.graph.Vertex;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AndrewMonotoneChainConvexHullCalculator implements ConvexHullCalculator {

  private static final Comparator<Vertex> minLatComparator =
      Comparator.comparingDouble((Vertex v) -> v.getCoordinates().getLatitude())
          .thenComparingDouble(v -> v.getCoordinates().getLongitude());

  @Override
  public List<Vertex> calculateConvexHull(Collection<Vertex> vertices) {
    if (vertices.size() <= 2) {
      return new ArrayList<>(vertices);
    }
    var sortedByLat = vertices.stream().sorted(minLatComparator).collect(Collectors.toList());

    var upperHull = new ArrayList<Vertex>();
    for (var vertex : sortedByLat) {
      filterCounterClockwise(upperHull, vertex);
    }

    var lowerHull = new ArrayList<Vertex>();
    for (int i = sortedByLat.size() - 1; i >= 0; i--) {
      var vertex = sortedByLat.get(i);
      filterCounterClockwise(lowerHull, vertex);
    }

    upperHull.remove(upperHull.size() - 1);
    lowerHull.remove(lowerHull.size() - 1);

    lowerHull.addAll(upperHull);
    return lowerHull;
  }

  private void filterCounterClockwise(ArrayList<Vertex> lowerHull, Vertex p) {
    while (lowerHull.size() >= 2
        && isCounterClockwiseTurn(
            p, lowerHull.get(lowerHull.size() - 1), lowerHull.get(lowerHull.size() - 2))) {
      lowerHull.remove(lowerHull.size() - 1);
    }
    lowerHull.add(p);
  }

  private boolean isCounterClockwiseTurn(Vertex p, Vertex q, Vertex r) {
    var pp = p.getCoordinates();
    var qq = q.getCoordinates();
    var rr = r.getCoordinates();

    return (qq.getLatitude() - rr.getLatitude()) * (pp.getLongitude() - rr.getLongitude())
        >= (qq.getLongitude() - rr.getLongitude()) * (pp.getLatitude() - rr.getLatitude());
  }
}
