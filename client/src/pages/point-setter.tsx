import React from 'react';
import { useMapEvents } from 'react-leaflet';
import { LatLng } from 'leaflet';

interface Props {
  addPoint: ({ lat, lng }: LatLng) => void;
}

const PointSetter = ({ addPoint }: Props) => {
  useMapEvents({
    click(e) {
      addPoint(e.latlng);
    },
  });
  return null;
};

export default PointSetter;
