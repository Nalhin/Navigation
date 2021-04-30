import { axios } from '../../axios';
import { Coordinates } from '../shared.types';
import { Bounds, PathResponse } from './pathfinding.types';
import {
  AlgorithmTypes,
  OptimizationTypes,
} from '../../../context/pathfinding-settings/pathfinding-settings-context';

export const getPathBetween = (
  start: Coordinates,
  end: Coordinates,
  algorithm: AlgorithmTypes,
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
  algorithm: AlgorithmTypes;
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
