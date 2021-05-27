import React from 'react';
import { AddressItem } from '../../types/address-item.type';
import AddressAutocomplete from '../address-autocomplete/address-autocomplete';
import { useMap } from '../../context/map-context/map-context';
import { Box } from '@material-ui/core';
import { css } from '@emotion/css';

interface Props {
  setCurrent: (item: AddressItem | null) => void;
}

const MapAddressAutocomplete = ({ setCurrent }: Props) => {
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
    <Box
      className={css`
        width: 280px;
        @media only screen and (max-width: 768px) {
          width: 160px;
        }
      `}
    >
      <AddressAutocomplete
        value={value}
        onValueSelected={(val) => setSearchedItem(val)}
        label="Find a location"
      />
    </Box>
  );
};

export default MapAddressAutocomplete;
