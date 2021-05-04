package com.navigation.pathfinder.exceptions;

import java.text.MessageFormat;

public class VertexNotPresentException extends RuntimeException {
  public VertexNotPresentException(long vertexId) {
    super(MessageFormat.format("Vertex with {0} id is not present in the graph", vertexId));
  }
}
