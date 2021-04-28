import { axios } from '../../axios';
import { ReverseGeocodeAddress } from './reverse-geocode.types';
import { Coordinates } from '../shared.types';

export const getReverseGeocode = ({ longitude, latitude }: Coordinates) => {
  return axios.get<ReverseGeocodeAddress>('/reverse-geocode', {
    params: {
      latitude,
      longitude,
    },
  });
};
