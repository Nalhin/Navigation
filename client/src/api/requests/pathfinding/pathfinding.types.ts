export interface PathResponse {
  simplePath: Node[];
  detailedPath: DetailedNode[];
  totalDistance: number;
  totalDuration: number;
  totalNodes: number;
}

export interface Node {
  latitude: number;
  longitude: number;
  id: number;
}

export interface DetailedNode {
  cumulativeDistance: number;
  distanceFromPrevious: number;
  cumulativeTime: number;
  timeFromPrevious: number;
  maxSpeedFromPrevious: number;
  totalPathPercentage: number;
  node: Node;
}
