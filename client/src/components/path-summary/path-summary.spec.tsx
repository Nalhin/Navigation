import { PathfindingAlgorithmTypes } from '../../constants/pathfinding-algorithms';
import { OptimizationTypes } from '../../constants/pathfinding-optimizations';
import { render, screen } from '@testing-library/react';
import PathSummary from './path-summary';
import React from 'react';

describe('PathSummary', () => {
  const pathSummary = {
    simplePath: [],
    searchBoundaries: [[]],
    totalDistance: 5,
    totalDuration: 0,
    totalNodes: 0,
    totalVisitedNodes: 10,
    executionDuration: 2.22,
    algorithm: PathfindingAlgorithmTypes.DIJKSTRA,
    optimization: OptimizationTypes.NONE,
    found: true,
  };

  it('should display path summary', () => {
    render(<PathSummary path={pathSummary} />);

    expect(screen.getByText(/Dijkstra/i)).toBeInTheDocument();
    expect(screen.getByText(/none/i)).toBeInTheDocument();
    expect(screen.getByText(/10/i)).toBeInTheDocument();
    expect(screen.getByText(/2.22 seconds/i)).toBeInTheDocument();
  });
});
