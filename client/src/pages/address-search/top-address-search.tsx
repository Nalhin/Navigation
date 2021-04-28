import React from 'react';
import { AddressItem } from '../list-item.type';
import AddressSearch from './address-search';
import { useMap } from '../../context/map/map-context';

interface Props {
  setCurrent: (item: AddressItem | null) => void;
}

const TopAddressSearch = ({ setCurrent }: Props) => {
  const { map } = useMap();
  const [value, setValue] = React.useState<AddressItem | null>(null);

  const setSearchedItem = (val: AddressItem | null) => {
    setValue(val);
    if (!val) {
      return;
    }
    map?.panTo({
      lat: val.location.latitude,
      lng: val.location.longitude,
    });
    setCurrent(val);
  };

  return (
    <AddressSearch
      value={value}
      onValueSet={(val) => setSearchedItem(val)}
      label="Find a location"
    />
  );
};

export default TopAddressSearch;
