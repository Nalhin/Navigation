import { uniqueId } from '../unique-id/unique-id';
import { AddressItem } from '../../types/address-item.type';
import { Coordinates } from '../../api/requests/shared.types';

const addressItemUniqueId = uniqueId();

export function createEmptyAddressItem(location: Coordinates): AddressItem {
  return {
    id: addressItemUniqueId.getAndIncrement(),
    city: 'Unknown',
    country: 'Unknown',
    houseNumber: 'Unknown',
    street: 'Unknown',
    postCode: 'Unknown',
    location,
  };
}
