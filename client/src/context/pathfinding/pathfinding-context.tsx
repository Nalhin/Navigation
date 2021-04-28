import React from 'react';
import { PathResponse } from '../../api/requests/pathfinding/pathfinding.types';
import { AddressItem } from '../../pages/list-item.type';
import { useMutation } from 'react-query';
import { getPathBetween } from '../../api/requests/pathfinding/pathfinding';
import { Coordinates } from '../../api/requests/shared.types';
import { usePathfindingSettings } from '../pathfinding-settings/pathfinding-settings-context';

const PathfindingContext = React.createContext<PathfindingContext | null>(null);

interface PathfindingContext {
  path: PathResponse | null;
  selectedPoints: SelectedPoints;
  setStart: (item: AddressItem | null) => void;
  setEnd: (item: AddressItem | null) => void;
  findPath: () => void;
}

export const usePathfinding = () => {
  const context = React.useContext(PathfindingContext);
  if (!context) {
    throw new Error(`usePathfinding must be used within a PathfindingContext`);
  }
  return context;
};

interface SelectedPoints {
  start: AddressItem | null;
  end: AddressItem | null;
}

export const PathfindingProvider: React.FC = ({ children }) => {
  const [selectedPoints, setSelectedPoints] = React.useState<SelectedPoints>({
    start: null,
    end: null,
  });
  const settings = usePathfindingSettings();

  const { data, mutate, reset } = useMutation(
    'path-between',
    (variables: { first: Coordinates; last: Coordinates }) =>
      getPathBetween(
        variables.first,
        variables.last,
        settings.algorithm,
        settings.optimization,
      ),
  );

  const setEnd = React.useCallback((end: AddressItem | null) => {
    setSelectedPoints((prev) => ({
      ...prev,
      end,
    }));
    reset();
  }, []);

  const setStart = React.useCallback((start: AddressItem | null) => {
    setSelectedPoints((prev) => ({
      ...prev,
      start,
    }));
    reset();
  }, []);

  const findPath = React.useCallback(() => {
    if (!selectedPoints.start || !selectedPoints.end) {
      return;
    }
    mutate({
      first: selectedPoints.start.location,
      last: selectedPoints.end.location,
    });
  }, [selectedPoints.start, selectedPoints.end]);

  return (
    <PathfindingContext.Provider
      value={{
        selectedPoints,
        setEnd,
        setStart,
        path: data?.data as PathResponse,
        findPath,
      }}
    >
      {children}
    </PathfindingContext.Provider>
  );
};
