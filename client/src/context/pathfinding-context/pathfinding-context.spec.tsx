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
import * as notistack from 'notistack';
import { usePathfindingSettings } from '../pathfinding-settings-context/pathfinding-settings-context';
import { exampleAddress } from '../../../test/constants/address';
import { examplePathResponse } from '../../../test/constants/path-response';
import { examplePathfindingSettings } from '../../../test/constants/pathfinding-settings';

jest.mock('../pathfinding-settings-context/pathfinding-settings-context');
jest.mock('../map-context/map-context');
jest.mock('notistack');

describe('PathfindingContext', () => {
  const server = setupServer();

  const mapMock = { fitBounds: jest.fn() };
  mocked(useMap).mockReturnValue({
    map: (mapMock as unknown) as L.Map,
    setMap: jest.fn(),
  });

  const pathfindingSettings = { ...examplePathfindingSettings };
  mocked(usePathfindingSettings).mockReturnValue(pathfindingSettings);

  const notistackMock = {
    enqueueSnackbar: jest.fn(),
    closeSnackbar: jest.fn(),
  };
  mocked(notistack).useSnackbar.mockReturnValue(notistackMock);

  const exampleAddressItem = { ...exampleAddress };

  beforeAll(() => server.listen());
  afterEach(() => {
    server.resetHandlers();
    jest.clearAllMocks();
  });
  afterAll(() => server.close());

  const pathResponse = { ...examplePathResponse, bounded: false };

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

  const wrapper: React.FC = ({ children }) => (
    <QueryClientProvider
      client={
        new QueryClient({
          defaultOptions: { queries: { retry: false } },
        })
      }
    >
      <PathfindingProvider>{children}</PathfindingProvider>
    </QueryClientProvider>
  );

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

    it('should call bounded pathfinding api', async () => {
      mocked(usePathfindingSettings).mockReturnValue({
        ...pathfindingSettings,
        bounded: true,
      });
      const boundedPathResponse = { ...pathResponse, numberOfNodes: 55 };
      server.use(
        rest.get<PathResponse>(
          '/api/pathfinding/path-between/bounded',
          (req, res, ctx) => {
            return res(ctx.status(200), ctx.json(boundedPathResponse));
          },
        ),
      );

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
        expect(result.current.path).toStrictEqual(boundedPathResponse);
      });
    });
  });

  it('should display error when start is not set', async () => {
    const { result, waitForNextUpdate } = renderHook(() => usePathfinding(), {
      wrapper,
    });

    act(() => {
      result.current.setEnd(exampleAddressItem);
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
