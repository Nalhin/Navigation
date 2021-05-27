import React from 'react';
import { Marker } from 'react-leaflet';
import { encodeMarkerIcon } from './encode-marker-icon';
import { Icon } from 'leaflet';
import { Coordinates } from '../../../api/requests/shared.types';

const red = encodeMarkerIcon(
  '<svg viewBox = "0 0 100 100" xmlns = "http://www.w3.org/2000/svg" ><metadata id="metadata1">image/svg+xml</metadata><circle fill="#FF1A00" cx="50" cy="50" r="50"/></svg>',
);

const endIcon = new Icon({
  iconUrl: red,
  iconSize: [20, 20],
});

interface Props {
  position: Coordinates;
}

const EndMarker = ({ position }: Props) => {
  return (
    <Marker
      icon={endIcon}
      draggable={false}
      position={{
        lat: position.latitude,
        lng: position.longitude,
      }}
    />
  );
};

export default EndMarker;
