import React from 'react';

export const PathfindingSettingsContext = React.createContext<PathfindingSettingsContextProps | null>(
  null,
);
export const SetPathfindingSettingsContext = React.createContext<SetPathfindingSettingsContextProps | null>(
  null,
);

export interface PathfindingSettingsContextProps {
  optimization: OptimizationTypes;
  algorithm: AlgorithmTypes;
}

export interface SetPathfindingSettingsContextProps {
  setAlgorithm: (algorithm: AlgorithmTypes) => void;
  setOptimization: (optimization: OptimizationTypes) => void;
}

export enum OptimizationTypes {
  DISTANCE = 'DISTANCE',
  TIME = 'TIME',
}

export enum AlgorithmTypes {
  DIJKSTRA = 'DIJKSTRA',
  A_STAR = 'A_STAR',
  BELLMAN_FORD = 'BELLMAN_FORD',
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
  algorithm: AlgorithmTypes.DIJKSTRA,
};

export const PathfindingSettingsProvider: React.FC<Props> = ({
  defaultPathfindingSettings = DEFAULT_SETTINGS,
  children,
}) => {
  const [
    setting,
    setSettings,
  ] = React.useState<PathfindingSettingsContextProps>({
    ...defaultPathfindingSettings,
  });

  const setAlgorithm = React.useCallback((algorithm: AlgorithmTypes) => {
    setSettings((prev) => ({ ...prev, algorithm }));
  }, []);

  const setOptimization = React.useCallback(
    (optimization: OptimizationTypes) => {
      setSettings((prev) => ({ ...prev, optimization }));
    },
    [],
  );

  return (
    <PathfindingSettingsContext.Provider value={setting}>
      <SetPathfindingSettingsContext.Provider
        value={{ setAlgorithm, setOptimization }}
      >
        {children}
      </SetPathfindingSettingsContext.Provider>
    </PathfindingSettingsContext.Provider>
  );
};
