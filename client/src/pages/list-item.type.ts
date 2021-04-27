import { GeoJsonPoint } from '../api/requests/shared.types';

export interface ListItem {
  id: number;
  location: GeoJsonPoint;
  city: string;
  country: string;
  houseNumber: string;
  street: string;
  postCode: string;
}
