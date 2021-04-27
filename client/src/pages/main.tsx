import React from 'react';
import Map from './map';
import DraggableList from './draggable-list/draggable-list';
import { ListItem } from './list-item.type';
import { useMutation } from 'react-query';
import { getPathBetween } from '../api/requests/pathfinding/pathfinding';
import { getReverseGeocode } from '../api/requests/reverse-geocode/reverse-geocode.requests';
import { LatLng } from 'leaflet';

const Main = () => {
  const [points, setPoints] = React.useState<ListItem[]>([]);

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
    { onSuccess: (resp) => setPoints((prev) => [...prev, resp.data]) },
  );

  const path =
    data?.data.points.map((point: any) => [point.longitude, point.latitude]) ??
    [];

  const addPoint = (mapClick: LatLng) => {
    addGeocodedPoint(mapClick);
  };

  return (
    <div>
      <DraggableList setItems={(items) => setPoints(items)} items={points} />
      <Map points={points} path={path} addPoint={addPoint} />
      <button onClick={() => mutate()}>Find</button>
    </div>
  );
};

export default Main;
