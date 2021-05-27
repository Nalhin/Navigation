import { MapProvider, useMap } from './map-context';
import React from 'react';
import { act, renderHook } from '@testing-library/react-hooks';
import L from 'leaflet';
import { waitFor } from '@testing-library/react';

describe('MapContext', () => {
  it('should allow to set and access map', () => {
    const wrapper: React.FC = ({ children }) => (
      <MapProvider>{children}</MapProvider>
    );
    const { result } = renderHook(() => useMap(), { wrapper });
    const map = jest.fn();

    act(() => {
      result.current.setMap((map as unknown) as L.Map);
    });

    waitFor(() => expect(result.current.map).toBe(map));
  });
});
