import { fireEvent, render } from '@testing-library/react';
import CurrentPoint from './current-point';
import React from 'react';
import { usePathfinding } from '../../context/pathfinding-context/pathfinding-context';
import { mocked } from 'ts-jest';
import { screen } from '@testing-library/react';

jest.mock('../../context/pathfinding-context/pathfinding-context');

describe('CurrentPoint', () => {
  const mockedUsePathfinding = {
    setStart: jest.fn(),
    setEnd: jest.fn(),
  };

  mocked(usePathfinding).mockReturnValue(
    (mockedUsePathfinding as unknown) as ReturnType<typeof usePathfinding>,
  );

  const item = {
    id: 1,
    location: { latitude: 1.1111, longitude: 2.2222 },
    city: 'city',
    country: 'country',
    houseNumber: 'house number',
    street: 'street',
    postCode: 'post code',
  };

  afterEach(() => jest.clearAllMocks());

  it('should display order item details', () => {
    render(<CurrentPoint item={item} />);

    expect(screen.getByText(/city/i)).toBeInTheDocument();
    expect(screen.getByText(/house number/i)).toBeInTheDocument();
    expect(screen.getByText(/street/i)).toBeInTheDocument();
    expect(screen.getByText(/2.2222/i)).toBeInTheDocument();
    expect(screen.getByText(/1.1111/i)).toBeInTheDocument();
  });

  it('should call setEnd when clicked', () => {
    render(<CurrentPoint item={item} />);

    fireEvent.click(screen.getByText(/set end/i));

    expect(mockedUsePathfinding.setEnd).toBeCalledWith(item);
  });

  it('should call setStart when clicked', () => {
    render(<CurrentPoint item={item} />);

    fireEvent.click(screen.getByText(/set start/i));

    expect(mockedUsePathfinding.setStart).toBeCalledWith(item);
  });
});
