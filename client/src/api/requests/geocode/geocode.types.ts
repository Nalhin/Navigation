import { Coordinates } from '../shared.types';

export interface GeocodeAddress {
  id: number;
  location: Coordinates;
  city: string;
  country: string;
  houseNumber: string;
  street: string;
  postCode: string;
}
