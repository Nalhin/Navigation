export interface PathResponse {
  simplePath: Node[];
  searchBoundaries: Node[];
  totalDistance: number;
  totalDuration: number;
  totalNodes: number;
  totalVisitedNodes: number;
  executionDuration: number;
}

export interface Bounds {
  minLatitude: number;
  maxLatitude: number;
  minLongitude: number;
  maxLongitude: number;
}

export interface Node {
  latitude: number;
  longitude: number;
  id: number;
}
