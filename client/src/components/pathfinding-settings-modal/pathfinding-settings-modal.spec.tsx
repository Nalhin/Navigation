import { setupServer } from 'msw/node';
import { examplePathfindingSettings } from '../../../test/constants/pathfinding-settings';
import { mocked } from 'ts-jest/utils';
import {
  usePathfindingSettings,
  useSetPathfindingSettings,
} from '../../context/pathfinding-settings-context/pathfinding-settings-context';
import { rest } from 'msw';
import { renderWithProviders } from '../../../test/render/render-with-providers';
import PathfindingSettingsModal from './pathfinding-settings-modal';
import { screen, waitFor } from '@testing-library/react';
import { PathfindingAlgorithmTypes } from '../../constants/pathfinding-algorithms';
import React from 'react';
import userEvent from '@testing-library/user-event';

jest.mock(
  '../../context/pathfinding-settings-context/pathfinding-settings-context',
);

describe('PathfindingSettingModal', () => {
  const server = setupServer();

  beforeAll(() => server.listen());
  afterEach(() => {
    server.resetHandlers();
    jest.clearAllMocks();
  });
  afterAll(() => server.close());

  const pathfindingSettings = {
    ...examplePathfindingSettings,
    algorithm: PathfindingAlgorithmTypes.DIJKSTRA,
  };
  const setSettings = jest.fn();
  mocked(usePathfindingSettings).mockReturnValue(pathfindingSettings);
  mocked(useSetPathfindingSettings).mockReturnValue({ setSettings });

  beforeEach(() => {
    server.use(
      rest.get(
        '/api/pathfinding/algorithms/DIJKSTRA/supported-optimizations',
        (req, res, ctx) => {
          return res(
            ctx.status(200),
            ctx.json(['TIME', 'DISTANCE', 'NUMBER_OF_NODES']),
          );
        },
      ),
    );
  });

  it('should be hidden when isOpen flag is false', () => {
    renderWithProviders(
      <PathfindingSettingsModal isOpen={false} onClose={jest.fn()} />,
    );

    expect(screen.queryByText(/settings/i)).not.toBeInTheDocument();
  });

  it('should allow modifying values', async () => {
    renderWithProviders(
      <PathfindingSettingsModal isOpen onClose={jest.fn()} />,
    );
    const minLatInput = await screen.findByLabelText(/min latitude/i);
    userEvent.type(minLatInput, '{selectall}{del}1.2233');
    userEvent.click(
      await screen.findByRole('button', { name: /save changes/i }),
    );

    await waitFor(() => {
      expect(setSettings).toBeCalledWith({
        ...pathfindingSettings,
        bounds: { ...pathfindingSettings.bounds, minLatitude: 1.2233 },
      });
    });
  });

  it('should close on submit', async () => {
    renderWithProviders(
      <PathfindingSettingsModal isOpen onClose={jest.fn()} />,
    );
    const minLatInput = await screen.findByLabelText(/min latitude/i);
    userEvent.type(minLatInput, '{selectall}{del}1.2233');
    userEvent.click(
      await screen.findByRole('button', { name: /save changes/i }),
    );

    await waitFor(() => {
      expect(setSettings).toBeCalledWith({
        ...pathfindingSettings,
        bounds: { ...pathfindingSettings.bounds, minLatitude: 1.2233 },
      });
    });
  });
});
