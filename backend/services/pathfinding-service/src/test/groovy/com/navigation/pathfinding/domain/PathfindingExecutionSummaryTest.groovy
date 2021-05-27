package com.navigation.pathfinding.domain

import com.navigation.pathfinder.pathfinding.PathSummary
import spock.lang.Specification

import java.time.Instant

class PathfindingExecutionSummaryTest extends Specification {

  def "executionDurationInSeconds() should return difference between start and and in second and 3 point precision"() {
    given:
    def start = Instant.ofEpochMilli(0)
    def end = start.plusMillis(555)
    def summary = new PathfindingExecutionSummary(
        Stub(PathSummary),
        start,
        end,
        PathfindingAlgorithms.DIJKSTRA,
        PathfindingOptimizations.TIME
    )
    when:
    def result = summary.executionDurationInSeconds()
    then:
    result == 0.555d
  }
}
