import React from 'react';
import Map from './map';
import { ListItem } from './list-item.type';
import { useMutation } from 'react-query';
import { getPathBetween } from '../api/requests/pathfinding/pathfinding';
import { getReverseGeocode } from '../api/requests/reverse-geocode/reverse-geocode.requests';
import L, { LatLng } from 'leaflet';
import { AxiosError } from 'axios';
import { uniqueId } from '../utils/unique-id';
import CurrentPoint from './current-point';
import Search from './search/search';

const Main = () => {
  const [points, setPoints] = React.useState<ListItem[]>([]);
  const [current, setCurrent] = React.useState<ListItem | null>(null);
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
        const item: ListItem = {
          ...data,
          location: { type: 'Point', coordinates: [call.lng, call.lat] },
        };
        setPoints((prev) => [...prev, item]);
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

          setPoints((prev) => [...prev, item]);
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

  return (
    <div>
      <div>
        <div>Path</div>
        <button onClick={() => mutate()}>Find</button>
      </div>
      <Search />
      <Map
        points={points}
        path={path}
        addPoint={addPoint}
        setMap={(map) => setMap(map)}
      />

      {current && <CurrentPoint item={current} />}
    </div>
  );
};
export default Main;
