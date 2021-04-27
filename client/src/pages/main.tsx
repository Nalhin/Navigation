import React from 'react';
import Map from './map';
import DraggableList from './draggable-list/draggable-list';
import { ListItem } from './list-item.type';
import { useMutation } from 'react-query';
import { getPathBetween } from '../api/requests/pathfinding/pathfinding';
import { getReverseGeocode } from '../api/requests/reverse-geocode/reverse-geocode.requests';
import L, { LatLng } from 'leaflet';
import { AxiosError } from 'axios';
import { uniqueId } from '../utils/unique-id';
import { Flex, Button, Heading } from '@chakra-ui/react';

const Main = () => {
  const [points, setPoints] = React.useState<ListItem[]>([]);
  const [map, setMap] = React.useState<null | L.Map>(null);

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
        setPoints((prev) => [
          ...prev,
          {
            ...data,
            location: { type: 'Point', coordinates: [call.lng, call.lat] },
          },
        ]);
      },
      onError: (error: AxiosError, call) => {
        if (error.response?.status === 404) {
          setPoints((prev) => [
            ...prev,
            {
              id: uniqueId(),
              city: 'Unknown',
              country: 'Unknown',
              houseNumber: 'Unknown',
              street: 'Unknown',
              postCode: 'Unknown',
              location: { type: 'Point', coordinates: [call.lng, call.lat] },
            },
          ]);
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

  const onElementClick = (index: number) => {
    const nums = [...points[index].location.coordinates].reverse() as [
      number,
      number,
    ];
    map?.panTo(nums);
  };

  return (
    <Flex direction={'row'}>
      <Flex w={'300px'} h="100vh" direction={'column'}>
        <Heading as="h3" size="lg" textAlign={'center'}>
          Path
        </Heading>
        <DraggableList
          setItems={(items) => setPoints(items)}
          items={points}
          onClick={onElementClick}
        />
        <Button mt={'auto'} onClick={() => mutate()} colorScheme="teal">
          Find
        </Button>
      </Flex>
      <Map
        points={points}
        path={path}
        addPoint={addPoint}
        setMap={(map) => setMap(map)}
      />
      ;
    </Flex>
  );
};

export default Main;
