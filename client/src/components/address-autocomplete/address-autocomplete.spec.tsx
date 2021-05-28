import { waitFor, screen } from '@testing-library/react';
import { renderWithProviders } from '../../../test/render/render-with-providers';
import AddressAutocomplete from './address-autocomplete';
import { setupServer } from 'msw/node';
import React from 'react';
import { rest } from 'msw';
import userEvent from '@testing-library/user-event';
import { exampleAddress } from '../../../test/constants/address';

describe('AddressAutocomplete', () => {
  const server = setupServer();

  beforeAll(() => server.listen());
  afterEach(() => server.resetHandlers());
  afterAll(() => server.close());

  const initialAddress = { ...exampleAddress };
  const apiAddress = {
    ...exampleAddress,
    id: 2,
    city: 'Warsaw',
  };

  beforeEach(() => {
    server.use(
      rest.get('/api/geocode', (req, res, ctx) => {
        return res(ctx.status(200), ctx.json([apiAddress]));
      }),
    );
  });

  it('should fetch list of addresses', async () => {
    renderWithProviders(
      <AddressAutocomplete
        onValueSelected={jest.fn()}
        label={'label'}
        value={initialAddress}
      />,
    );

    userEvent.click(screen.getByLabelText(/label/i));

    await waitFor(() =>
      expect(screen.getByText(/warsaw/i)).toBeInTheDocument(),
    );
  });

  it('should call onValueSelected after address is selected', async () => {
    const onValueSelected = jest.fn();
    renderWithProviders(
      <AddressAutocomplete
        onValueSelected={onValueSelected}
        label={'label'}
        value={initialAddress}
      />,
    );

    userEvent.click(screen.getByLabelText(/label/i));
    userEvent.click(await screen.findByText(/warsaw/i));

    await waitFor(() => expect(onValueSelected).toBeCalledWith(apiAddress));
  });
});
