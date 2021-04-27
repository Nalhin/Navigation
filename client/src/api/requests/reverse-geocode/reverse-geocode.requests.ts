import { axios } from '../../axios';
import { ReverseGeocodeAddress } from './reverse-geocode.types';

export const getReverseGeocode = ({
  longitude,
  latitude,
}: {
  latitude: number;
  longitude: number;
}) => {
  return axios.get<ReverseGeocodeAddress>('/reverse-geocode', {
    params: {
      latitude,
      longitude,
    },
  });
};
