import * as reactQuery from 'react-query';
import { render, screen } from '@testing-library/react';
import GlobalLoader from './global-loader';
import React from 'react';

describe('GlobalLoader', () => {
  beforeEach(() => jest.clearAllMocks());

  it('should display loader when mutating', () => {
    jest.spyOn(reactQuery, 'useIsFetching').mockReturnValue(1);
    jest.spyOn(reactQuery, 'useIsMutating').mockReturnValue(0);

    render(<GlobalLoader />);

    expect(screen.getByLabelText(/loader/i)).toBeInTheDocument();
  });

  it('should display loader when fetching', () => {
    jest.spyOn(reactQuery, 'useIsFetching').mockReturnValue(0);
    jest.spyOn(reactQuery, 'useIsMutating').mockReturnValue(1);

    render(<GlobalLoader />);

    expect(screen.getByLabelText(/loader/i)).toBeInTheDocument();
  });

  it('should hide loader when not fetching and not mutating', () => {
    jest.spyOn(reactQuery, 'useIsFetching').mockReturnValue(0);
    jest.spyOn(reactQuery, 'useIsMutating').mockReturnValue(0);

    render(<GlobalLoader />);

    expect(screen.queryByLabelText(/loader/i)).not.toBeInTheDocument();
  });
});
