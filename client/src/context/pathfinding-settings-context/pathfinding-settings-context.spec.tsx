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
import { examplePathfindingSettings } from '../../../test/constants/pathfinding-settings';

describe('MapContext', () => {
  const pathfindingSettings = {
    ...examplePathfindingSettings,
  };

  it('should return default setting', () => {
    const wrapper: React.FC = ({ children }) => (
      <PathfindingSettingsProvider
        defaultPathfindingSettings={pathfindingSettings}
      >
        {children}
      </PathfindingSettingsProvider>
    );
    const { result } = renderHook(() => usePathfindingSettings(), { wrapper });

    expect(result.current).toEqual(pathfindingSettings);
  });

  it('should allow to modify pathfinding settings', () => {
    const wrapper: React.FC = ({ children }) => (
      <PathfindingSettingsProvider
        defaultPathfindingSettings={pathfindingSettings}
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
      ...pathfindingSettings,
      algorithm: PathfindingAlgorithmTypes.BELLMAN_FORD,
      optimization: OptimizationTypes.DISTANCE,
    };

    act(() => {
      resultSetter.current.setSettings({
        ...pathfindingSettings,
        algorithm: PathfindingAlgorithmTypes.BELLMAN_FORD,
        optimization: OptimizationTypes.DISTANCE,
      });
    });

    waitFor(() => expect(result.current).toEqual(expectedSettings));
  });
});
