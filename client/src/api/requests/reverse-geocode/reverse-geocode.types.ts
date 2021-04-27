import { GeoJsonPoint } from '../shared.types';

export interface ReverseGeocodeAddress {
  id: number;
  location: GeoJsonPoint;
  city: string;
  country: string;
  houseNumber: string;
  street: string;
  postCode: string;
}
