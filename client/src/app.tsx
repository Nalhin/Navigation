import React from 'react';
import { QueryClient, QueryClientProvider } from 'react-query';
import Main from './pages/main';
import GlobalLoader from './components/global-loader/global-loader';
import { CssBaseline } from '@material-ui/core';
import { PathfindingSettingsProvider } from './context/pathfinding-settings-context/pathfinding-settings-context';
import { MapProvider } from './context/map-context/map-context';
import { PathfindingProvider } from './context/pathfinding-context/pathfinding-context';
import { SnackbarProvider } from 'notistack';

const queryClient = new QueryClient();

const App = () => {
  return (
    <SnackbarProvider maxSnack={3}>
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
    </SnackbarProvider>
  );
};

export default App;
