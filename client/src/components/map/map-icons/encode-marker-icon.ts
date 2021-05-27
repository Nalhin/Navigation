import { Icon } from 'leaflet';

export const encodeMarkerIcon = (str: string) => {
  return 'data:image/svg+xml,' + encodeURIComponent(str);
};

const green = encodeMarkerIcon(
  '<svg viewBox = "0 0 100 100" xmlns = "http://www.w3.org/2000/svg" ><metadata id="metadata1">image/svg+xml</metadata><circle fill="#00FF00" cx="50" cy="50" r="50"/></svg>',
);

export const startIcon = new Icon({
  iconUrl: green,
  iconSize: [20, 20],
});
