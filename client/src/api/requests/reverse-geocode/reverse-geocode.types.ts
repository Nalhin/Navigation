import { Coordinates } from '../shared.types';

export interface ReverseGeocodeAddress {
  id: number;
  location: Coordinates;
  city: string;
  country: string;
  houseNumber: string;
  street: string;
  postCode: string;
}
