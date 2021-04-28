import React from 'react';
import { QueryClient, QueryClientProvider } from 'react-query';
import Main from './pages/main';
import GlobalLoader from './pages/global-loader';
import { CssBaseline } from '@material-ui/core';
import { PathfindingSettingsProvider } from './context/pathfinding-settings/pathfinding-settings-context';
import { MapProvider } from './context/map/map-context';
import { PathfindingProvider } from './context/pathfinding/pathfinding-context';

const queryClient = new QueryClient();

const App = () => {
  return (
    <QueryClientProvider client={queryClient}>
      <MapProvider>
        <PathfindingSettingsProvider>
          <PathfindingProvider>
            <CssBaseline />
            <GlobalLoader />
            <Main />
          </PathfindingProvider>
        </PathfindingSettingsProvider>
      </MapProvider>
    </QueryClientProvider>
  );
};

export default App;
