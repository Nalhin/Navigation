import React from 'react';
import Map from '../components/map/map';
import { AddressItem } from '../types/address-item.type';
import { useMutation } from 'react-query';
import { getReverseGeocode } from '../api/requests/reverse-geocode/reverse-geocode.requests';
import { LatLng } from 'leaflet';
import { AxiosError } from 'axios';
import { Box } from '@material-ui/core';
import PathDrawerMenu from '../components/path-drawer-menu/path-drawer-menu';
import CurrentPoint from '../components/current-point/current-point';
import { usePathfinding } from '../context/pathfinding-context/pathfinding-context';
import MapLocationFinder from '../components/map-location-finder/map-location-finder';
import { createEmptyAddressItem } from '../utils/create-empty-address-item/create-empty-address-item';
import { Coordinates } from '../api/requests/shared.types';

const Main = () => {
  const [current, setCurrent] = React.useState<AddressItem | null>(null);
  const [isOpen, setIsOpen] = React.useState(false);
  const pathfinding = usePathfinding();

  const { mutate: addGeocodedPoint } = useMutation(
    (coordinates: Coordinates) => getReverseGeocode(coordinates),
    {
      onSuccess: (resp, args) => {
        setCurrent({
          ...resp.data,
          location: args,
        });
      },
      onError: (error: AxiosError, args) => {
        if (error.response?.status === 404) {
          setCurrent(createEmptyAddressItem(args));
        }
      },
    },
  );

  const addPoint = (mapClick: LatLng) => {
    addGeocodedPoint({ latitude: mapClick.lat, longitude: mapClick.lng });
  };

  return (
    <div>
      <PathDrawerMenu isOpen={isOpen} onClose={() => setIsOpen(false)} />
      <Box position={'relative'}>
        <Box
          position="absolute"
          left={48}
          top={16}
          zIndex={1000}
          display={isOpen ? 'none' : 'block'}
        >
          <MapLocationFinder
            onMenuClick={() => setIsOpen(true)}
            setCurrent={setCurrent}
            onDirectionClick={() => {
              pathfinding.setEnd(current);
              setIsOpen(true);
            }}
          />
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
