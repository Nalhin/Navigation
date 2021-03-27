import { axios } from '../axios';
import { LatLngLiteral } from 'leaflet';

export const getPath = (start: LatLngLiteral, end: LatLngLiteral) => {
  return axios.get('find-path', {
    params: {
      startLatitude: start.lat,
      startLongitude: start.lng,
      endLatitude: end.lat,
      endLongitude: end.lng,
    },
  });
};
