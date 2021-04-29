package com.navigation.pathfinder.convexhull;

import com.navigation.pathfinder.graph.Vertex;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AndrewMonotoneChainConvexHullCalculator implements ConvexHullCalculator {

  private static final Comparator<Vertex> vertexComparator =
      Comparator.comparingDouble((Vertex v) -> v.getCoordinates().getLatitude())
          .thenComparingDouble(v -> v.getCoordinates().getLongitude());

  @Override
  public List<Vertex> calculateConvexHull(Collection<Vertex> points) {
    return buildHull(points.stream().sorted(vertexComparator).collect(Collectors.toList()));
  }

  public List<Vertex> buildHull(List<Vertex> points) {
    if (points.size() <= 1) return new ArrayList<>(points);
    var upperHull = new ArrayList<Vertex>();
    for (var p : points) {
      filterHull(upperHull, p);
    }
    upperHull.remove(upperHull.size() - 1);

    var lowerHull = new ArrayList<Vertex>();
    for (int i = points.size() - 1; i >= 0; i--) {
      var p = points.get(i);
      filterHull(lowerHull, p);
    }
    lowerHull.remove(lowerHull.size() - 1);

    if (!(upperHull.size() == 1 && upperHull.equals(lowerHull))) upperHull.addAll(lowerHull);
    return upperHull;
  }

  private void filterHull(ArrayList<Vertex> lowerHull, Vertex p) {
    while (lowerHull.size() >= 2) {
      var q = lowerHull.get(lowerHull.size() - 1);
      var r = lowerHull.get(lowerHull.size() - 2);
      if (isGreater(p, q, r)) lowerHull.remove(lowerHull.size() - 1);
      else break;
    }
    lowerHull.add(p);
  }

  private boolean isGreater(Vertex p, Vertex q, Vertex r) {
    var pp = p.getCoordinates();
    var qq = q.getCoordinates();
    var rr = r.getCoordinates();

    return (qq.getLatitude() - rr.getLatitude()) * (pp.getLongitude() - rr.getLongitude())
        >= (qq.getLongitude() - rr.getLongitude()) * (pp.getLatitude() - rr.getLatitude());
  }
}
