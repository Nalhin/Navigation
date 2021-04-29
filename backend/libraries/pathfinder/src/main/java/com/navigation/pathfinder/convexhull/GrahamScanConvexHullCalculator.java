package com.navigation.pathfinder.convexhull;

import com.navigation.pathfinder.graph.Vertex;

import java.util.*;
import java.util.stream.Collectors;

public class GrahamScanConvexHullCalculator implements ConvexHullCalculator {

  private static final Comparator<Vertex> minLatComparator =
      Comparator.comparingDouble((Vertex v) -> v.getCoordinates().getLatitude())
          .thenComparingDouble(v -> v.getCoordinates().getLongitude());

  @Override
  public List<Vertex> calculateConvexHull(Collection<Vertex> vertices) {
    if (vertices.size() <= 2) {
      return new ArrayList<>(vertices);
    }

    final var source = Collections.min(vertices, minLatComparator);

    var remaining =
        vertices.stream()
            .filter(v -> !v.equals(source))
            .sorted(Comparator.comparingDouble((target) -> angleFromSource(source, target)))
            .collect(Collectors.toList());

    List<Vertex> result = new ArrayList<>();
    result.add(source);
    result.add(remaining.get(0));
    result.add(remaining.get(1));

    for (int i = 2; i < remaining.size(); i++) {
      var curr = remaining.get(i);
      while (result.size() >= 2
          && isClockwiseTurn(result.get(result.size() - 2), result.get(result.size() - 1), curr)) {
        result.remove(result.size() - 1);
      }
      result.add(curr);
    }

    return result;
  }

  private boolean isClockwiseTurn(Vertex p, Vertex q, Vertex r) {
    var pp = p.getCoordinates();
    var qq = q.getCoordinates();
    var rr = r.getCoordinates();

    return (qq.getLatitude() - rr.getLatitude()) * (pp.getLongitude() - rr.getLongitude())
        <= (qq.getLongitude() - rr.getLongitude()) * (pp.getLatitude() - rr.getLatitude());
  }

  private double angleFromSource(Vertex source, Vertex target) {
    double latDiff = source.getCoordinates().getLatitude() - target.getCoordinates().getLatitude();
    double lngDiff =
        source.getCoordinates().getLongitude() - target.getCoordinates().getLongitude();
    return Math.atan2(latDiff, lngDiff);
  }
}
