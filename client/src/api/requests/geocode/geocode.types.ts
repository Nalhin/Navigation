import { GeoJsonPoint } from '../shared.types';

export interface GeocodeAddress {
  id: number;
  location: GeoJsonPoint;
  city: string;
  country: string;
  houseNumber: string;
  street: string;
  postCode: string;
}
