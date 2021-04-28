import { Coordinates  } from '../api/requests/shared.types';

export interface AddressItem {
  id: number;
  location: Coordinates;
  city: string;
  country: string;
  houseNumber: string;
  street: string;
  postCode: string;
}
