import React from 'react';
import L from 'leaflet';

export const MapContext = React.createContext<MapContextProps | null>(null);

interface MapContextProps {
  map: L.Map | null;
  setMap: (map: L.Map) => void;
}

export const useMap = () => {
  const context = React.useContext(MapContext);
  if (!context) {
    throw new Error(`useMap must be used within a MapContextProvider`);
  }
  return context;
};

export const MapProvider: React.FC = ({ children }) => {
  const [map, setMapState] = React.useState<L.Map | null>(null);

  const setMap = React.useCallback((newMap: L.Map) => {
    setMapState(newMap);
  }, []);

  return (
    <MapContext.Provider value={{ map, setMap }}>
      {children}
    </MapContext.Provider>
  );
};
