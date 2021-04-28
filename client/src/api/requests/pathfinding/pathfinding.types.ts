export interface PathResponse {
  simplePath: Node[];
  totalDistance: number;
  totalDuration: number;
  totalNodes: number;
  totalVisitedNodes: number;
  executionDuration: number;
}

export interface Node {
  latitude: number;
  longitude: number;
  id: number;
}
