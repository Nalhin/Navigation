import { AddressItem } from '../../types/address-item.type';
import { Divider, Grid, IconButton, Paper } from '@material-ui/core';
import MenuIcon from '@material-ui/icons/Menu';
import MapAddressAutocomplete from './map-address-autocomplete';
import SearchIcon from '@material-ui/icons/Search';
import DirectionsIcon from '@material-ui/icons/Directions';
import React from 'react';

interface Props {
  onMenuClick: () => void;
  setCurrent: (item: AddressItem | null) => void;
  onDirectionClick: () => void;
}

const MapLocationFinder = ({
  setCurrent,
  onDirectionClick,
  onMenuClick,
}: Props) => {
  return (
    <Paper>
      <Grid container>
        <IconButton aria-label="menu" onClick={onMenuClick}>
          <MenuIcon />
        </IconButton>
        <MapAddressAutocomplete setCurrent={setCurrent} />
        <IconButton type="submit" aria-label="search">
          <SearchIcon />
        </IconButton>
        <Divider orientation="vertical" />
        <IconButton
          color="primary"
          aria-label="directions"
          onClick={onDirectionClick}
        >
          <DirectionsIcon />
        </IconButton>
      </Grid>
    </Paper>
  );
};

export default MapLocationFinder;
