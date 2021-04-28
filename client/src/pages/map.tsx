import React from 'react';
import { MapContainer, Marker, Polyline, TileLayer } from 'react-leaflet';
import { LatLng } from 'leaflet';
import 'leaflet/dist/leaflet.css';
import { AddressItem } from './list-item.type';
import PointSetter from './point-setter';
import { useMap } from '../context/map/map-context';
import { usePathfinding } from '../context/pathfinding/pathfinding-context';
import { currIcon, endIcon, startIcon } from './map-icons/icons';

const CENTER = {
  lat: 50.049683,
  lng: 19.944544,
};

interface Props {
  currPoint: AddressItem | null;
  addPoint: ({ lat, lng }: LatLng) => void;
}

const Map = ({ addPoint, currPoint }: Props) => {
  const map = useMap();
  const pathfinding = usePathfinding();
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
        {currPoint && (
          <Marker
            icon={currIcon()}
            draggable={false}
            position={{
              lat: currPoint.location.latitude,
              lng: currPoint.location.longitude,
            }}
          />
        )}
        {pathfinding.selectedPoints.start && (
          <Marker
            icon={startIcon()}
            draggable={false}
            position={{
              lat: pathfinding.selectedPoints.start.location.latitude,
              lng: pathfinding.selectedPoints.start.location.longitude,
            }}
          />
        )}
        {pathfinding.selectedPoints.end && (
          <Marker
            icon={endIcon()}
            draggable={false}
            position={{
              lat: pathfinding.selectedPoints.end.location.latitude,
              lng: pathfinding.selectedPoints.end.location.longitude,
            }}
          />
        )}
        <Polyline
          pathOptions={{ color: 'blue' }}
          positions={(pathfinding.path ?? { points: [] }).points.map(
            (item) => ({
              lat: item.longitude,
              lng: item.latitude,
            }),
          )}
        />
        <PointSetter addPoint={addPoint} />
      </MapContainer>
    </>
  );
};

export default Map;
