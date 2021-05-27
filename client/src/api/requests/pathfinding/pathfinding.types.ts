import { OptimizationTypes } from '../../../constants/optimizations';
import { AlgorithmTypes } from '../../../constants/algorithms';

export interface PathResponse {
  simplePath: MapNode[];
  searchBoundaries: MapNode[][];
  totalDistance: number;
  totalDuration: number;
  totalNodes: number;
  totalVisitedNodes: number;
  executionDuration: number;
  algorithm: AlgorithmTypes;
  optimization: OptimizationTypes;
  found: boolean;
}

export interface Bounds {
  minLatitude: number;
  maxLatitude: number;
  minLongitude: number;
  maxLongitude: number;
}

export interface MapNode {
  latitude: number;
  longitude: number;
  id: number;
}
