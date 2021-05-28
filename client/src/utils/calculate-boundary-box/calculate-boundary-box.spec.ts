import { calculateBoundaryBox } from './calculate-boundary-box';

describe('CalculateBoundaryBox', () => {
  it('should return boundary box determined by  coordinates', () => {
    const coordinates = [
      { latitude: 1, longitude: 2 },
      { latitude: 99, longitude: 1 },
      { latitude: 0, longitude: 99 },
    ];

    const result = calculateBoundaryBox(coordinates);

    expect(result).toStrictEqual([
      [0, 1],
      [99, 99],
    ]);
  });
});
