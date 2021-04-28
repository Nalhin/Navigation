import React from 'react';
import { MapContainer, Marker, Polyline, TileLayer } from 'react-leaflet';
import L, { LatLng } from 'leaflet';
import 'leaflet/dist/leaflet.css';
import { ListItem } from './list-item.type';
import PointSetter from './point-setter';
import { useMap } from '../context/settings/map-context';

const CENTER = {
  lat: 50.049683,
  lng: 19.944544,
};

const mySvgString =
  '<svg viewBox = "0 0 100 100" xmlns = "http://www.w3.org/2000/svg" ><metadata id="metadata1">image/svg+xml</metadata><circle fill="#FF1A00" cx="50" cy="50" r="50"/></svg>';
const url = 'data:image/svg+xml,' + encodeURIComponent(mySvgString);

L.Marker.prototype.options.icon = L.icon({
  iconUrl: url,
  iconSize: [20, 20],
});

interface Props {
  points: ListItem[];
  currPoint: ListItem | null;
  path: [number, number][];
  addPoint: ({ lat, lng }: LatLng) => void;
}

const Map = ({ points, path, addPoint, currPoint }: Props) => {
  const map = useMap();
  return (
    <>
      <MapContainer
        center={CENTER}
        zoom={15}
        scrollWheelZoom={false}
        style={{ height: '100vh', width: '100%' }}
        whenCreated={map.setMap}
      >
        <TileLayer
          attribution='&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
          url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
        />
        {points.map((point) => {
          return (
            <Marker
              key={point.id}
              draggable={false}
              position={[
                point.location.coordinates[1],
                point.location.coordinates[0],
              ]}
            />
          );
        })}
        {currPoint && (
          <Marker
            draggable={false}
            position={{
              lat: currPoint.location.coordinates[1],
              lng: currPoint.location.coordinates[0],
            }}
          />
        )}
        <Polyline pathOptions={{ color: 'blue' }} positions={path} />
        <PointSetter addPoint={addPoint} />
      </MapContainer>
    </>
  );
};

export default Map;
