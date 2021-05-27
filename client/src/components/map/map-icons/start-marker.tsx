import { Icon } from 'leaflet';
import { encodeMarkerIcon } from './encode-marker-icon';
import { Coordinates } from '../../../api/requests/shared.types';
import { Marker } from 'react-leaflet';
import React from 'react';

const green = encodeMarkerIcon(
  '<svg viewBox = "0 0 100 100" xmlns = "http://www.w3.org/2000/svg" ><metadata id="metadata1">image/svg+xml</metadata><circle fill="#00FF00" cx="50" cy="50" r="50"/></svg>',
);

export const startIcon = new Icon({
  iconUrl: green,
  iconSize: [20, 20],
});

interface Props {
  position: Coordinates;
}

const StartMarker = ({ position }: Props) => {
  return (
    <Marker
      icon={startIcon}
      draggable={false}
      position={{
        lat: position.latitude,
        lng: position.longitude,
      }}
    />
  );
};

export default StartMarker;
