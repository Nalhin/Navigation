import { axios } from '../../axios';
import { Coordinates } from '../shared.types';
import { PathResponse } from './pathfinding.types';

export const getPathBetween = (start: Coordinates, end: Coordinates) => {
  return axios.get<PathResponse>('/pathfinding/path-between', {
    params: {
      startLatitude: start.latitude,
      startLongitude: start.longitude,
      endLatitude: end.latitude,
      endLongitude: end.longitude,
    },
  });
};
