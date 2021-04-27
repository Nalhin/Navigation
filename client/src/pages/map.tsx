import React from 'react';
import { MapContainer, Marker, Polyline, TileLayer } from 'react-leaflet';
import L, { LatLng } from 'leaflet';
import 'leaflet/dist/leaflet.css';
import { ListItem } from './list-item.type';
import PointSetter from './point-setter';

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
  path: [number, number][];
  addPoint: ({ lat, lng }: LatLng) => void;
  setMap: (map: L.Map) => void;
}

const Map = ({ setMap, points, path, addPoint }: Props) => {
  return (
    <>
      <MapContainer
        center={CENTER}
        zoom={15}
        scrollWheelZoom={false}
        style={{ height: '100vh', width: '100%' }}
        whenCreated={setMap}
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
              eventHandlers={{
                dragend: console.log,
                drag: console.log,
                dragstart: console.log,
              }}
              position={[
                point.location.coordinates[1],
                point.location.coordinates[0],
              ]}
            />
          );
        })}
        <Polyline pathOptions={{ color: 'blue' }} positions={path} />
        <PointSetter addPoint={addPoint} />
      </MapContainer>
    </>
  );
};

export default Map;
