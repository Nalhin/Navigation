import { Icon } from 'leaflet';

const encode = (str: string) => {
  return 'data:image/svg+xml,' + encodeURIComponent(str);
};

const orange = encode(
  '<svg viewBox = "0 0 100 100" xmlns = "http://www.w3.org/2000/svg" ><metadata id="metadata1">image/svg+xml</metadata><circle fill="#FF8000" cx="50" cy="50" r="50"/></svg>',
);
const green = encode(
  '<svg viewBox = "0 0 100 100" xmlns = "http://www.w3.org/2000/svg" ><metadata id="metadata1">image/svg+xml</metadata><circle fill="#00FF00" cx="50" cy="50" r="50"/></svg>',
);
const red = encode(
  '<svg viewBox = "0 0 100 100" xmlns = "http://www.w3.org/2000/svg" ><metadata id="metadata1">image/svg+xml</metadata><circle fill="#FF1A00" cx="50" cy="50" r="50"/></svg>',
);

export const currIcon = () => {
  return new Icon({
    iconUrl: orange,
    iconSize: [20, 20],
  });
};

export const startIcon = () => {
  return new Icon({
    iconUrl: green,
    iconSize: [20, 20],
  });
};

export const endIcon = () => {
  return new Icon({
    iconUrl: red,
    iconSize: [20, 20],
  });
};
