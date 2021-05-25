import { OptimizationTypes } from '../../../constants/optimizations';
import { AlgorithmTypes } from '../../../constants/algorithms';

export interface PathResponse {
  simplePath: Node[];
  searchBoundaries: Node[][];
  totalDistance: number;
  totalDuration: number;
  totalNodes: number;
  totalVisitedNodes: number;
  executionDuration: number;
  algorithm: AlgorithmTypes;
  optimization: OptimizationTypes;
  found: true;
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
