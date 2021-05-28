import { QueryClient, QueryClientProvider } from 'react-query';
import { render } from '@testing-library/react';
import React from 'react';

export const renderWithProviders = (ui: JSX.Element) => {
  const queryClient = new QueryClient({
    defaultOptions: { queries: { retry: false } },
  });

  return {
    ...render(
      <QueryClientProvider client={queryClient}>{ui}</QueryClientProvider>,
    ),
    queryClient,
  };
};
