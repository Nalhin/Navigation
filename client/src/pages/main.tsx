import React from 'react';
import Map from './map';
import { ListItem } from './list-item.type';
import { useMutation } from 'react-query';
import { getPathBetween } from '../api/requests/pathfinding/pathfinding';
import { getReverseGeocode } from '../api/requests/reverse-geocode/reverse-geocode.requests';
import { LatLng } from 'leaflet';
import { AxiosError } from 'axios';
import { uniqueId } from '../utils/unique-id';
import CurrentPoint from './current-point';
import Search from './search/search';
import { Box, Grid, IconButton, Paper } from '@material-ui/core';
import MenuIcon from '@material-ui/icons/Menu';
import Drawer from './drawer/drawer';
import { useMap } from '../context/settings/map-context';

const Main = () => {
  const [points, setPoints] = React.useState<ListItem[]>([]);
  const [current, setCurrent] = React.useState<ListItem | null>(null);
  const { map } = useMap();
  const [isOpen, setIsOpen] = React.useState(false);

  const { mutate, data } = useMutation(() => {
    const firstPoint = points[0].location.coordinates;
    const secondPoint = points[1].location.coordinates;
    return getPathBetween(
      { lat: firstPoint[1], lng: firstPoint[0] },
      { lat: secondPoint[1], lng: secondPoint[1] },
    );
  });

  const { mutate: addGeocodedPoint } = useMutation(
    ({ lat, lng }: LatLng) =>
      getReverseGeocode({ latitude: lat, longitude: lng }),
    {
      onSuccess: (resp, call) => {
        const data = resp.data;
        const item: ListItem = {
          ...data,
          location: { type: 'Point', coordinates: [call.lng, call.lat] },
        };
        setCurrent(item);
      },
      onError: (error: AxiosError, call) => {
        if (error.response?.status === 404) {
          const item: ListItem = {
            id: uniqueId(),
            city: 'Unknown',
            country: 'Unknown',
            houseNumber: 'Unknown',
            street: 'Unknown',
            postCode: 'Unknown',
            location: { type: 'Point', coordinates: [call.lng, call.lat] },
          };
          setCurrent(item);
        }
      },
    },
  );

  const path =
    data?.data.points.map((point: any) => [point.longitude, point.latitude]) ??
    [];

  const addPoint = (mapClick: LatLng) => {
    addGeocodedPoint(mapClick);
  };

  const setSearchedItem = (value: ListItem | null) => {
    if (!value) {
      return;
    }
    map?.panTo({
      lat: value.location.coordinates[1],
      lng: value.location.coordinates[0],
    });
    setCurrent(value);
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
              <Search onValueSet={setSearchedItem} />
            </Grid>
          </Paper>
        </Box>
        <Map
          currPoint={current}
          points={points}
          path={path}
          addPoint={addPoint}
        />
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
      <div>
        <div>Path</div>
        <button onClick={() => mutate()}>Find</button>
      </div>
    </div>
  );
};
export default Main;
