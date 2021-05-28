import { fireEvent, render, screen, waitFor } from '@testing-library/react';
import React from 'react';
import MapLocationFinder from './map-location-finder';
import MapLocationFinderAutocomplete from './map-location-finder-autocomplete';
import { mocked } from 'ts-jest/utils';

jest.mock('./map-location-finder-autocomplete.tsx');
mocked(MapLocationFinderAutocomplete).mockReturnValue(<></>);

describe('MapLocationFinder', () => {
  const props = {
    onMenuClick: jest.fn(),
    setCurrent: jest.fn(),
    onDirectionClick: jest.fn(),
  };

  it('should call onDirectionClick when direction icon is clicked', () => {
    const onDirectionClick = jest.fn();
    render(
      <MapLocationFinder {...props} onDirectionClick={onDirectionClick} />,
    );

    fireEvent.click(screen.getByLabelText(/directions/i));

    waitFor(() => expect(onDirectionClick).toBeCalledTimes(1));
  });

  it('should call onMenuClick when menu is clicked', () => {
    const onMenuClick = jest.fn();
    render(<MapLocationFinder {...props} onMenuClick={onMenuClick} />);

    fireEvent.click(screen.getByLabelText(/menu/i));

    waitFor(() => expect(onMenuClick).toBeCalledTimes(1));
  });
});
