import React from 'react';
import { QueryClient, QueryClientProvider } from 'react-query';
import Main from './pages/main';
import GlobalLoader from './pages/global-loader';
import { CssBaseline } from '@material-ui/core';
import { PathfindingSettingsProvider } from './context/settings/pathfinding-settings-context';
import { MapProvider } from './context/settings/map-context';

const queryClient = new QueryClient();

const App = () => {
  return (
    <MapProvider>
      <PathfindingSettingsProvider>
        <QueryClientProvider client={queryClient}>
          <CssBaseline />
          <GlobalLoader />
          <Main />
        </QueryClientProvider>
      </PathfindingSettingsProvider>
    </MapProvider>
  );
};

export default App;
