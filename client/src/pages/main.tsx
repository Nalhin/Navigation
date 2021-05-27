import React from 'react';
import Map from '../components/map/map';
import { AddressItem } from '../types/address-item.type';
import { useMutation } from 'react-query';
import { getReverseGeocode } from '../api/requests/reverse-geocode/reverse-geocode.requests';
import { LatLng } from 'leaflet';
import { AxiosError } from 'axios';
import { uniqueId } from '../utils/unique-id';
import { Box } from '@material-ui/core';
import PathDrawerMenu from '../components/path-drawer-menu/path-drawer-menu';
import CurrentPoint from '../components/current-point/current-point';
import { usePathfinding } from '../context/pathfinding-context/pathfinding-context';
import MapLocationFinder from '../components/map-location-finder/map-location-finder';

const Main = () => {
  const [current, setCurrent] = React.useState<AddressItem | null>(null);
  const [isOpen, setIsOpen] = React.useState(false);
  const pathfinding = usePathfinding();

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
