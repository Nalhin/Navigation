import React from 'react';
import {
  MapContainer,
  Marker,
  Polyline,
  Popup,
  TileLayer,
} from 'react-leaflet';
import L, { Marker as LeafletMarker } from 'leaflet';
import 'leaflet/dist/leaflet.css';
import icon from 'leaflet/dist/images/marker-icon.png';
import iconShadow from 'leaflet/dist/images/marker-shadow.png';
import { useMutation } from 'react-query';
import { getPath } from '../api/requests/pathfinding';

L.Marker.prototype.options.icon = L.icon({
  iconUrl: icon,
  shadowUrl: iconShadow,
});

const CENTER = {
  lat: 50.049683,
  lng: 19.944544,
};

const POINTS = [
  {
    lat: 50.048683,
    lng: 19.93544,
  },
  {
    lat: 50.049683,
    lng: 19.944544,
  },
];

const Map = ({}) => {
  const refPoint1 = React.useRef<null | LeafletMarker<any>>(null);
  const refPoint2 = React.useRef<null | LeafletMarker<any>>(null);

  const { mutate, data } = useMutation(() =>
    getPath(refPoint1.current!?.getLatLng(), refPoint2.current!?.getLatLng()),
  );

  const points =
    data?.data.points.map((point: any) => [point.longitude, point.latitude]) ??
    [];

  return (
    <>
      <MapContainer
        center={CENTER}
        zoom={15}
        scrollWheelZoom={false}
        style={{ height: '100vh', width: '100vw' }}
      >
        <TileLayer
          attribution='&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
          url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
        />
        <Marker ref={refPoint1} title={'start'} draggable position={POINTS[0]}>
          <Popup>START</Popup>
        </Marker>
        <Marker ref={refPoint2} title={'end'} draggable position={POINTS[1]}>
          <Popup>END</Popup>
        </Marker>
        <Polyline pathOptions={{ color: 'blue' }} positions={points} />
      </MapContainer>
      <button onClick={() => mutate()}>Find</button>
    </>
  );
};

export default Map;
