import { axios } from '../../axios';
import { LatLngLiteral } from 'leaflet';

export const getPathBetween = (start: LatLngLiteral, end: LatLngLiteral) => {
  return axios.get('/pathfinding/path-between', {
    params: {
      startLatitude: start.lat,
      startLongitude: start.lng,
      endLatitude: end.lat,
      endLongitude: end.lng,
    },
  });
};
