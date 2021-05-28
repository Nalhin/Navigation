import React from 'react';
import { act, renderHook } from '@testing-library/react-hooks';
import {
  PathfindingSettingsProvider,
  usePathfindingSettings,
  useSetPathfindingSettings,
} from './pathfinding-settings-context';
import { OptimizationTypes } from '../../constants/pathfinding-optimizations';
import { PathfindingAlgorithmTypes } from '../../constants/pathfinding-algorithms';
import { waitFor } from '@testing-library/react';

describe('MapContext', () => {
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

  it('should return default setting', () => {
    const wrapper: React.FC = ({ children }) => (
      <PathfindingSettingsProvider
        defaultPathfindingSettings={DEFAULT_SETTINGS}
      >
        {children}
      </PathfindingSettingsProvider>
    );
    const { result } = renderHook(() => usePathfindingSettings(), { wrapper });

    expect(result.current).toEqual(DEFAULT_SETTINGS);
  });

  it('should allow to modify pathfinding settings', () => {
    const wrapper: React.FC = ({ children }) => (
      <PathfindingSettingsProvider
        defaultPathfindingSettings={DEFAULT_SETTINGS}
      >
        {children}
      </PathfindingSettingsProvider>
    );
    const { result } = renderHook(() => usePathfindingSettings(), { wrapper });
    const { result: resultSetter } = renderHook(
      () => useSetPathfindingSettings(),
      {
        wrapper,
      },
    );
    const expectedSettings = {
      ...DEFAULT_SETTINGS,
      algorithm: PathfindingAlgorithmTypes.BELLMAN_FORD,
      optimization: OptimizationTypes.DISTANCE,
    };

    act(() => {
      resultSetter.current.setSettings({
        ...DEFAULT_SETTINGS,
        algorithm: PathfindingAlgorithmTypes.BELLMAN_FORD,
        optimization: OptimizationTypes.DISTANCE,
      });
    });

    waitFor(() => expect(result.current).toEqual(expectedSettings));
  });
});
