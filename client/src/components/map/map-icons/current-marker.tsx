import React from 'react';
import { Marker } from 'react-leaflet';
import { encodeMarkerIcon } from './encode-marker-icon';
import { Icon } from 'leaflet';
import { Coordinates } from '../../../api/requests/shared.types';

const orange = encodeMarkerIcon(
  '<svg viewBox = "0 0 100 100" xmlns = "http://www.w3.org/2000/svg" ><metadata id="metadata1">image/svg+xml</metadata><circle fill="#FF8000" cx="50" cy="50" r="50"/></svg>',
);

const currIcon = new Icon({
  iconUrl: orange,
  iconSize: [20, 20],
});

interface Props {
  position: Coordinates;
}

const CurrentMarker = ({ position }: Props) => {
  return (
    <Marker
      icon={currIcon}
      draggable={false}
      position={{
        lat: position.latitude,
        lng: position.longitude,
      }}
    />
  );
};

export default CurrentMarker;
