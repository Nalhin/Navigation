import React from 'react';
import Map from './map';
import { AddressItem } from './list-item.type';
import { useMutation } from 'react-query';
import { getReverseGeocode } from '../api/requests/reverse-geocode/reverse-geocode.requests';
import { LatLng } from 'leaflet';
import { AxiosError } from 'axios';
import { uniqueId } from '../utils/unique-id';
import { Box, Grid, IconButton, Paper } from '@material-ui/core';
import MenuIcon from '@material-ui/icons/Menu';
import Drawer from './drawer/drawer';
import CurrentPoint from './current-point/current-point';
import TopAddressSearch from './address-search/top-address-search';

const Main = () => {
  const [current, setCurrent] = React.useState<AddressItem | null>(null);
  const [isOpen, setIsOpen] = React.useState(false);

  const { mutate: addGeocodedPoint } = useMutation(
    ({ lat, lng }: LatLng) =>
      getReverseGeocode({ latitude: lat, longitude: lng }),
    {
      onSuccess: (resp, call) => {
        const data = resp.data;
        const item: AddressItem = {
          ...data,
          location: { latitude: call.lat, longitude: call.lng },
        };
        setCurrent(item);
      },
      onError: (error: AxiosError, call) => {
        if (error.response?.status === 404) {
          const item: AddressItem = {
            id: uniqueId(),
            city: 'Unknown',
            country: 'Unknown',
            houseNumber: 'Unknown',
            street: 'Unknown',
            postCode: 'Unknown',
            location: { latitude: call.lat, longitude: call.lng },
          };
          setCurrent(item);
        }
      },
    },
  );
  const addPoint = (mapClick: LatLng) => {
    addGeocodedPoint(mapClick);
  };

  return (
    <div>
      <Drawer isOpen={isOpen} onClose={() => setIsOpen(false)} />
      <Box ml={isOpen ? '300px' : 0} position={'relative'}>
        <Box
          position="absolute"
          left={48}
          top={16}
          zIndex={1000}
          display={isOpen && 'none'}
        >
          <Paper>
            <Grid container direction="row">
              <IconButton
                aria-label="open-menu"
                color="primary"
                onClick={() => setIsOpen(true)}
              >
                <MenuIcon />
              </IconButton>
              <TopAddressSearch setCurrent={setCurrent} />
            </Grid>
          </Paper>
        </Box>
        <Map currPoint={current} addPoint={addPoint} />
        <Box
          position="absolute"
          width={200}
          bottom={24}
          right={24}
          zIndex={1000}
        >
          {current && <CurrentPoint item={current} />}
        </Box>
      </Box>
    </div>
  );
};
export default Main;
