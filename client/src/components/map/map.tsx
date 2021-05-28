import React from 'react';
import {
  MapContainer,
  Polygon,
  Polyline,
  Rectangle,
  TileLayer,
} from 'react-leaflet';
import { LatLng } from 'leaflet';
import 'leaflet/dist/leaflet.css';
import { AddressItem } from '../../types/address-item.type';
import MapOnclickPointSetter from './map-onclick-point-setter';
import { useMap } from '../../context/map-context/map-context';
import { usePathfinding } from '../../context/pathfinding-context/pathfinding-context';
import MapLayers from './map-layers';
import { usePathfindingSettings } from '../../context/pathfinding-settings-context/pathfinding-settings-context';
import CurrentMarker from './map-icons/current-marker';
import EndMarker from './map-icons/end-marker';
import StartMarker from './map-icons/start-marker';

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
  const settings = usePathfindingSettings();
  return (
    <>
      <MapContainer
        center={CENTER}
        zoom={15}
        style={{ height: '100vh', width: '100%' }}
        whenCreated={map.setMap}
      >
        <TileLayer
          attribution='&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
          url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
        />
        {currPoint && <CurrentMarker position={currPoint.location} />}
        {pathfinding.selectedPoints.start && (
          <StartMarker position={pathfinding.selectedPoints.start.location} />
        )}
        {pathfinding.selectedPoints.end && (
          <EndMarker position={pathfinding.selectedPoints.end.location} />
        )}
        {pathfinding.path &&
          pathfinding.path.searchBoundaries.map((poly, index) => (
            <Polygon
              key={index}
              pathOptions={{ color: '#b19cd9' }}
              positions={poly.map((item) => ({
                lat: item.latitude,
                lng: item.longitude,
              }))}
            />
          ))}
        {settings.bounded && (
          <Rectangle
            color="lightblue"
            bounds={[
              [settings.bounds.maxLatitude, settings.bounds.maxLongitude],
              [settings.bounds.minLatitude, settings.bounds.minLongitude],
            ]}
          />
        )}
        <Polyline
          pathOptions={{ color: 'blue' }}
          positions={(pathfinding.path?.simplePath ?? []).map((item) => ({
            lat: item.latitude,
            lng: item.longitude,
          }))}
        />
        <MapOnclickPointSetter addPoint={addPoint} />
        <MapLayers />
      </MapContainer>
    </>
  );
};

export default Map;
