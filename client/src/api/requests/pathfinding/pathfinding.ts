import { axios } from '../../axios';
import { Coordinates } from '../shared.types';
import { Bounds, PathResponse } from './pathfinding.types';
import { OptimizationTypes } from '../../../constants/pathfinding-optimizations';
import { PathfindingAlgorithmTypes } from '../../../constants/pathfinding-algorithms';

export const getPathBetween = (
  start: Coordinates,
  end: Coordinates,
  algorithm: PathfindingAlgorithmTypes,
  optimization: OptimizationTypes,
) => {
  return axios.get<PathResponse>('/pathfinding/path-between', {
    params: {
      startLatitude: start.latitude,
      startLongitude: start.longitude,
      endLatitude: end.latitude,
      endLongitude: end.longitude,
      algorithm,
      optimization,
    },
  });
};

export const getPathBetweenBounded = ({
  start,
  end,
  algorithm,
  optimization,
  bounds,
}: {
  start: Coordinates;
  end: Coordinates;
  algorithm: PathfindingAlgorithmTypes;
  optimization: OptimizationTypes;
  bounds: Bounds;
}) => {
  return axios.get<PathResponse>('/pathfinding/path-between/bounded', {
    params: {
      startLatitude: start.latitude,
      startLongitude: start.longitude,
      endLatitude: end.latitude,
      endLongitude: end.longitude,
      algorithm,
      optimization,
      ...bounds,
    },
  });
};

export const getAvailableOptimizationsForAlgorithm = (
  algorithm: PathfindingAlgorithmTypes,
) =>
  axios.get<OptimizationTypes[]>(
    `/pathfinding/algorithms/${algorithm}/supported-optimizations`,
  );
