import React from 'react';
import { Bounds } from '../../api/requests/pathfinding/pathfinding.types';
import { OptimizationTypes } from '../../constants/pathfinding-optimizations';
import { PathfindingAlgorithmTypes } from '../../constants/pathfinding-algorithms';

export const PathfindingSettingsContext = React.createContext<PathfindingSettingsContextProps | null>(
  null,
);
export const SetPathfindingSettingsContext = React.createContext<SetPathfindingSettingsContextProps | null>(
  null,
);

export interface PathfindingSettingsContextProps {
  optimization: OptimizationTypes;
  algorithm: PathfindingAlgorithmTypes;
  bounded: boolean;
  bounds: Bounds;
}

export interface SetPathfindingSettingsContextProps {
  setSettings: (settings: PathfindingSettingsContextProps) => void;
}

export const usePathfindingSettings = () => {
  const context = React.useContext(PathfindingSettingsContext);
  if (!context) {
    throw new Error(
      `usePathfindingSettings must be used within a PathfindingSettingsProvider`,
    );
  }
  return context;
};

export const useSetPathfindingSettings = () => {
  const context = React.useContext(SetPathfindingSettingsContext);
  if (!context) {
    throw new Error(
      `useChangePathfindingSettings must be used within a PathfindingSettingsProvider`,
    );
  }
  return context;
};

interface Props {
  defaultPathfindingSettings?: PathfindingSettingsContextProps;
}

const DEFAULT_SETTINGS = {
  optimization: OptimizationTypes.TIME,
  algorithm: PathfindingAlgorithmTypes.DIJKSTRA,
  bounded: false,
  bounds: {
    minLatitude: 50.0468,
    minLongitude: 19.9172,
    maxLatitude: 50.0562,
    maxLongitude: 19.9427,
  },
};

export const PathfindingSettingsProvider: React.FC<Props> = ({
  defaultPathfindingSettings = DEFAULT_SETTINGS,
  children,
}) => {
  const [setting, setSetting] = React.useState<PathfindingSettingsContextProps>(
    {
      ...defaultPathfindingSettings,
    },
  );

  const setSettings = React.useCallback(
    (settings: PathfindingSettingsContextProps) => {
      setSetting((prev) => ({ ...prev, ...settings }));
    },
    [],
  );

  return (
    <PathfindingSettingsContext.Provider value={setting}>
      <SetPathfindingSettingsContext.Provider value={{ setSettings }}>
        {children}
      </SetPathfindingSettingsContext.Provider>
    </PathfindingSettingsContext.Provider>
  );
};
