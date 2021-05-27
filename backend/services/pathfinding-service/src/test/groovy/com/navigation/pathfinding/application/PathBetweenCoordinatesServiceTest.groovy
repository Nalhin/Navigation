package com.navigation.pathfinding.application

import com.navigation.pathfinder.graph.Coordinates
import com.navigation.pathfinder.graph.Graph
import com.navigation.pathfinder.graph.GraphBuilder
import com.navigation.pathfinder.graph.Vertex
import com.navigation.pathfinding.domain.PathfindingAlgorithms
import com.navigation.pathfinding.domain.PathfindingOptimizations
import com.navigation.pathfinding.domain.PathfindingStrategyFactory
import io.vavr.control.Option
import spock.lang.Specification

import java.time.Clock

class PathBetweenCoordinatesServiceTest extends Specification {

  static class InitializedGraph {

    final Coordinates startCoords
    final Coordinates endCoords
    final Vertex startVertex
    final Vertex endVertex
    final Graph graph

    InitializedGraph(Coordinates startCoords, Coordinates endCoords, Vertex startVertex,
        Vertex endVertex, Graph graph) {
      this.startCoords = startCoords
      this.endCoords = endCoords
      this.startVertex = startVertex
      this.endVertex = endVertex
      this.graph = graph
    }
  }

  static InitializedGraph initializeGraphElements() {
    def start = new Coordinates(3, 4)
    def end = new Coordinates(1, 2)
    def startVertex = new Vertex(1, start)
    def endVertex = new Vertex(2, end)
    def graph = new GraphBuilder().addVertex(startVertex).addVertex(endVertex)
        .connectByIds(1, 2, 50).asGraph()

    return new InitializedGraph(start, end, startVertex, endVertex, graph)
  }

  def "calculatePathBetween() should return START_AND_END_NOT_FOUND error when both vertices are not found"() {
    given:
    def graphElements = initializeGraphElements()
    def graphRepository = GroovyMock(GraphRepository) {
      closestVertex(graphElements.startCoords, _) >> Option.none()
      closestVertex(graphElements.endCoords, _) >> Option.none()
      prepareGraph() >> graphElements.graph
    }
    def query = new PathBetweenCoordinatesUseCase.PathBetweenCoordinatesQuery(
        graphElements.startCoords,
        graphElements.endCoords,
        PathfindingAlgorithms.BIDIRECTIONAL_BFS,
        PathfindingOptimizations.NUMBER_OF_NODES
    )
    def service = new PathBetweenCoordinatesService(
        graphRepository,
        new PathfindingStrategyFactory(),
        Clock.systemUTC()
    )
    when:
    def result = service.calculatePathBetween(query)
    then:
    result.left ==
        PathBetweenCoordinatesUseCase.PathBetweenCoordinatesErrors.START_AND_END_NOT_FOUND
  }


  def "calculatePathBetween() should return START_NOT_FOUND error when start vertex is not found"() {
    given:
    def graphElements = initializeGraphElements()
    def graphRepository = GroovyMock(GraphRepository) {
      closestVertex(graphElements.startCoords, _) >> Option.none()
      closestVertex(graphElements.endCoords, _) >> Option.of(graphElements.endVertex)
      prepareGraph() >> graphElements.graph
    }
    def query = new PathBetweenCoordinatesUseCase.PathBetweenCoordinatesQuery(
        graphElements.startCoords,
        graphElements.endCoords,
        PathfindingAlgorithms.BIDIRECTIONAL_BFS,
        PathfindingOptimizations.NUMBER_OF_NODES
    )
    def service = new PathBetweenCoordinatesService(
        graphRepository,
        new PathfindingStrategyFactory(),
        Clock.systemUTC()
    )
    when:
    def result = service.calculatePathBetween(query)
    then:
    result.left ==
        PathBetweenCoordinatesUseCase.PathBetweenCoordinatesErrors.START_NOT_FOUND
  }

  def "calculatePathBetween() should return END_NOT_FOUND error when end vertex is not found"() {
    given:
    def graphElements = initializeGraphElements()
    def graphRepository = GroovyMock(GraphRepository) {
      closestVertex(graphElements.startCoords, _) >> Option.of(graphElements.startVertex)
      closestVertex(graphElements.endCoords, _) >> Option.none()
      prepareGraph() >> graphElements.graph
    }
    def query = new PathBetweenCoordinatesUseCase.PathBetweenCoordinatesQuery(
        graphElements.startCoords,
        graphElements.endCoords,
        PathfindingAlgorithms.BIDIRECTIONAL_BFS,
        PathfindingOptimizations.NUMBER_OF_NODES
    )
    def service = new PathBetweenCoordinatesService(
        graphRepository,
        new PathfindingStrategyFactory(),
        Clock.systemUTC()
    )
    when:
    def result = service.calculatePathBetween(query)
    then:
    result.left ==
        PathBetweenCoordinatesUseCase.PathBetweenCoordinatesErrors.END_NOT_FOUND
  }


  def "calculatePathBetween() should return OPTIMIZATION_NOT_SUPPORTER error when optimization is not supported"() {
    given:
    def graphElements = initializeGraphElements()
    def graphRepository = GroovyMock(GraphRepository) {
      closestVertex(graphElements.startCoords, _) >> Option.of(graphElements.startVertex)
      closestVertex(graphElements.endCoords, _) >> Option.of(graphElements.endVertex)
      prepareGraph() >> graphElements.graph

    }
    def query = new PathBetweenCoordinatesUseCase.PathBetweenCoordinatesQuery(
        graphElements.startCoords,
        graphElements.endCoords,
        PathfindingAlgorithms.BIDIRECTIONAL_BFS,
        PathfindingOptimizations.TIME
    )
    def service = new PathBetweenCoordinatesService(
        graphRepository,
        new PathfindingStrategyFactory(),
        Clock.systemUTC()
    )
    when:
    def result = service.calculatePathBetween(query)
    then:
    result.left ==
        PathBetweenCoordinatesUseCase.PathBetweenCoordinatesErrors.OPTIMIZATION_NOT_SUPPORTED
  }

  def "calculatePathBetween() should return path between start and end vertices"() {
    given:
    def graphElements = initializeGraphElements()
    def graphRepository = GroovyMock(GraphRepository) {
      closestVertex(graphElements.startCoords, _) >> Option.of(graphElements.startVertex)
      closestVertex(graphElements.endCoords, _) >> Option.of(graphElements.endVertex)
      prepareGraph() >> graphElements.graph

    }
    def query = new PathBetweenCoordinatesUseCase.PathBetweenCoordinatesQuery(
        graphElements.startCoords,
        graphElements.endCoords,
        PathfindingAlgorithms.BIDIRECTIONAL_BFS,
        PathfindingOptimizations.NUMBER_OF_NODES
    )
    def service = new PathBetweenCoordinatesService(
        graphRepository,
        new PathfindingStrategyFactory(),
        Clock.systemUTC()
    )
    when:
    def result = service.calculatePathBetween(query)
    then:
    verifyAll(result.get()) {
      algorithm() == PathfindingAlgorithms.BIDIRECTIONAL_BFS
      optimization() == PathfindingOptimizations.NUMBER_OF_NODES
      pathSummary().simplePath() == [graphElements.startVertex, graphElements.endVertex]
      pathSummary().isFound()
    }
  }
}
