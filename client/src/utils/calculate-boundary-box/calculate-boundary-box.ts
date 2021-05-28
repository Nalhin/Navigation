import { Coordinates } from '../../api/requests/shared.types';

export function calculateBoundaryBox(
  coords: Coordinates[],
): [number, number][] {
  const latitudes = coords.map((coordinate) => coordinate.latitude);
  const longitudes = coords.map((coordinate) => coordinate.longitude);
  return [
    [Math.min(...latitudes), Math.min(...longitudes)],
    [Math.max(...latitudes), Math.max(...longitudes)],
  ];
}
