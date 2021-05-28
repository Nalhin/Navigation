import { OptimizationTypes } from '../../src/constants/pathfinding-optimizations';
import { PathfindingAlgorithmTypes } from '../../src/constants/pathfinding-algorithms';

export const examplePathfindingSettings = {
  optimization: OptimizationTypes.TIME,
  algorithm: PathfindingAlgorithmTypes.DIJKSTRA,
  bounded: false,
  bounds: {
    minLatitude: 50.0468,
    minLongitude: 19.9172,
    maxLatitude: 50.0562,
    maxLongitude: 19.9427,
  },
};
