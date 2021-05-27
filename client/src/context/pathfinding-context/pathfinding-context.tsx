import React from 'react';
import { PathResponse } from '../../api/requests/pathfinding/pathfinding.types';
import { AddressItem } from '../../types/address-item.type';
import { useMutation } from 'react-query';
import {
  getPathBetween,
  getPathBetweenBounded,
} from '../../api/requests/pathfinding/pathfinding';
import { Coordinates } from '../../api/requests/shared.types';
import { usePathfindingSettings } from '../pathfinding-settings-context/pathfinding-settings-context';
import { useMap } from '../map-context/map-context';
import { AxiosError } from 'axios';
import { useSnackbar } from 'notistack';

const PathfindingContext = React.createContext<PathfindingContext | null>(null);

export interface PathfindingContext {
  path: PathResponse | null;
  selectedPoints: SelectedPoints;
  setStart: (item: AddressItem | null) => void;
  setEnd: (item: AddressItem | null) => void;
  swapStartAndEnd: () => void;
  clear: () => void;
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
  const { map } = useMap();
  const snackbar = useSnackbar();

  const { data, mutate, reset } = useMutation(
    ['path-between'],
    ({ start, end }: { start: Coordinates; end: Coordinates }) =>
      settings.bounded
        ? getPathBetweenBounded({
            start,
            end,
            algorithm: settings.algorithm,
            optimization: settings.optimization,
            bounds: settings.bounds,
          })
        : getPathBetween(start, end, settings.algorithm, settings.optimization),
    {
      onSuccess: (data) => {
        const latitudes = data.data.searchBoundaries.flatMap((el) =>
          el.map((e) => e.latitude),
        );
        const longitudes = data.data.searchBoundaries.flatMap((el) =>
          el.map((e) => e.longitude),
        );
        map?.fitBounds([
          [Math.min(...latitudes), Math.min(...longitudes)],
          [Math.max(...latitudes), Math.max(...longitudes)],
        ]);
      },
      onError: (error: AxiosError) => {
        snackbar.enqueueSnackbar(
          error.response?.data.message ?? 'Unexpected error',
          {
            variant: 'error',
            anchorOrigin: {
              vertical: 'top',
              horizontal: 'right',
            },
          },
        );
      },
    },
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

  const clear = React.useCallback(() => {
    setSelectedPoints((prev) => ({
      ...prev,
      start: null,
      end: null,
    }));
    reset();
  }, []);

  const swapStartAndEnd = React.useCallback(() => {
    setSelectedPoints((prev) => ({
      ...prev,
      end: prev.start,
      start: prev.end,
    }));
    reset();
  }, []);

  const findPath = React.useCallback(() => {
    if (!selectedPoints.start || !selectedPoints.end) {
      return;
    }
    mutate({
      start: selectedPoints.start.location,
      end: selectedPoints.end.location,
    });
  }, [selectedPoints.start, selectedPoints.end]);

  return (
    <PathfindingContext.Provider
      value={{
        clear,
        swapStartAndEnd,
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
