import { PathfindingAlgorithmTypes } from '../../src/constants/pathfinding-algorithms';
import { OptimizationTypes } from '../../src/constants/pathfinding-optimizations';

export const examplePathResponse = {
  simplePath: [],
  searchBoundaries: [[]],
  totalDistance: 1,
  totalDuration: 2,
  totalNodes: 3,
  totalVisitedNodes: 4,
  executionDuration: 5,
  algorithm: PathfindingAlgorithmTypes.BFS,
  optimization: OptimizationTypes.NUMBER_OF_NODES,
  found: false,
};
