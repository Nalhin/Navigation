import { OptimizationTypes } from './pathfinding-optimizations';
import { PathfindingAlgorithmTypes } from './pathfinding-algorithms';

export const DEFAULT_PATHFINDING_BOUNDS = {
  minLatitude: 52.10956942664077,
  maxLatitude: 52.34378514647381,
  minLongitude: 20.77444580584201,
  maxLongitude: 21.212715378992122,
};

export const PATHFINDING_SETTINGS = {
  optimization: OptimizationTypes.TIME,
  algorithm: PathfindingAlgorithmTypes.DIJKSTRA,
  bounded: false,
  bounds: DEFAULT_PATHFINDING_BOUNDS,
};
