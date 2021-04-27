import { axios } from '../../axios';
import { GeocodeAddress } from './geocode.types';

export const getGeocode = (address: string) => {
  return axios.get<GeocodeAddress[]>('/geocode', {
    params: {
      address,
    },
  });
};
