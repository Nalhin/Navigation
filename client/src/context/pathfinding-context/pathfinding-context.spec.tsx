import React from 'react';
import { act, renderHook } from '@testing-library/react-hooks';
import { PathfindingProvider, usePathfinding } from './pathfinding-context';
import { useMap } from '../map-context/map-context';
import { mocked } from 'ts-jest/utils';
import L from 'leaflet';
import { QueryClient, QueryClientProvider } from 'react-query';
import { setupServer } from 'msw/node';
import { rest } from 'msw';
import { PathResponse } from '../../api/requests/pathfinding/pathfinding.types';
import { PathfindingAlgorithmTypes } from '../../constants/pathfinding-algorithms';
import { OptimizationTypes } from '../../constants/pathfinding-optimizations';
import * as notistack from 'notistack';
import { usePathfindingSettings } from '../pathfinding-settings-context/pathfinding-settings-context';

jest.mock('../pathfinding-settings-context/pathfinding-settings-context');
jest.mock('../map-context/map-context');
jest.mock('notistack');

const queryClient = new QueryClient({
  defaultOptions: { queries: { retry: false } },
});

describe('PathfindingContext', () => {
  const server = setupServer();

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
  const notistackMock = {
    enqueueSnackbar: jest.fn(),
    closeSnackbar: jest.fn(),
  };
  const mapMock = { fitBounds: jest.fn() };

  mocked(usePathfindingSettings).mockReturnValue(DEFAULT_SETTINGS);
  mocked(useMap).mockReturnValue({
    map: (mapMock as unknown) as L.Map,
    setMap: jest.fn(),
  });
  mocked(notistack).useSnackbar.mockReturnValue(notistackMock);

  beforeAll(() => server.listen());
  afterEach(() => {
    server.resetHandlers();
    jest.clearAllMocks();
  });
  afterAll(() => server.close());

  const wrapper: React.FC = ({ children }) => (
    <QueryClientProvider client={queryClient}>
      <PathfindingProvider>{children}</PathfindingProvider>
    </QueryClientProvider>
  );

  const exampleAddressItem = {
    id: 1,
    location: { latitude: 1, longitude: 2 },
    city: 'city',
    country: 'country',
    houseNumber: 'house number',
    street: 'street',
    postCode: 'post code',
  };

  const pathResponse = {
    simplePath: [],
    searchBoundaries: [[]],
    totalDistance: 1,
    totalDuration: 2,
    totalNodes: 3,
    totalVisitedNodes: 4,
    executionDuration: 5,
    algorithm: PathfindingAlgorithmTypes.BFS,
    optimization: OptimizationTypes.NUMBER_OF_NODES,
    found: false,
  };

  beforeEach(() => {
    server.use(
      rest.get<PathResponse>(
        '/api/pathfinding/path-between',
        (req, res, ctx) => {
          return res(ctx.status(200), ctx.json(pathResponse));
        },
      ),
    );
  });

  describe('setStart', () => {
    it('should allow setting start point', async () => {
      const { result, waitForNextUpdate } = renderHook(() => usePathfinding(), {
        wrapper,
      });

      act(() => {
        result.current.setStart(exampleAddressItem);
      });
      await waitForNextUpdate();

      expect(result.current.selectedPoints.start).toStrictEqual(
        exampleAddressItem,
      );
    });
  });

  describe('setEnd', () => {
    it('should allow setting end point', async () => {
      const { result, waitForNextUpdate } = renderHook(() => usePathfinding(), {
        wrapper,
      });

      act(() => {
        result.current.setStart(exampleAddressItem);
      });
      await waitForNextUpdate();

      expect(result.current.selectedPoints.start).toStrictEqual(
        exampleAddressItem,
      );
    });
  });

  describe('swapStartAndEnd', () => {
    it('should swap start point and end point', async () => {
      const { result, waitForNextUpdate } = renderHook(() => usePathfinding(), {
        wrapper,
      });

      act(() => {
        result.current.setStart({ ...exampleAddressItem, id: 2 });
        result.current.setEnd({ ...exampleAddressItem, id: 3 });
        result.current.swapStartAndEnd();
      });
      await waitForNextUpdate();

      expect(result.current.selectedPoints.start?.id).toBe(3);
      expect(result.current.selectedPoints.end?.id).toBe(2);
    });
  });

  describe('clear', () => {
    it('should reset start point and end point', async () => {
      const { result, waitForNextUpdate } = renderHook(() => usePathfinding(), {
        wrapper,
      });
      act(() => {
        result.current.setStart(exampleAddressItem);
        result.current.setEnd(exampleAddressItem);
      });

      act(() => {
        result.current.clear();
      });
      await waitForNextUpdate();

      expect(result.current.selectedPoints.start).toBeNull();
      expect(result.current.selectedPoints.end).toBeNull();
    });
  });

  describe('findPath', () => {
    it('should call pathfinding api', async () => {
      const { result, waitFor } = renderHook(() => usePathfinding(), {
        wrapper,
      });
      act(() => {
        result.current.setStart(exampleAddressItem);
        result.current.setEnd(exampleAddressItem);
      });

      act(() => {
        result.current.findPath();
      });

      await waitFor(() => {
        expect(mapMock.fitBounds).toBeCalledTimes(1);
        expect(result.current.path).toStrictEqual(pathResponse);
      });
    });
  });

  it('should display error when start is not set', async () => {
    const { result, waitForNextUpdate } = renderHook(() => usePathfinding(), {
      wrapper,
    });

    act(() => {
      result.current.setStart(exampleAddressItem);
      result.current.findPath();
    });
    await waitForNextUpdate();

    expect(notistackMock.enqueueSnackbar).toBeCalledTimes(1);
  });

  it('should display error when end is not set', async () => {
    const { result, waitForNextUpdate } = renderHook(() => usePathfinding(), {
      wrapper,
    });

    act(() => {
      result.current.setStart(exampleAddressItem);
      result.current.findPath();
    });
    await waitForNextUpdate();

    expect(notistackMock.enqueueSnackbar).toBeCalledTimes(1);
  });
});
