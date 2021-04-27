import React from 'react';
import { QueryClient, QueryClientProvider } from 'react-query';
import Main from './pages/main';
import GlobalLoader from './pages/global-loader';
import { CssBaseline } from '@material-ui/core';

const queryClient = new QueryClient();

const App = () => {
  return (
    <QueryClientProvider client={queryClient}>
      <CssBaseline />
      <GlobalLoader />
      <Main />
    </QueryClientProvider>
  );
};

export default App;
