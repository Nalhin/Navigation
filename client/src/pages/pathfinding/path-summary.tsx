import React from 'react';
import { PathResponse } from '../../api/requests/pathfinding/pathfinding.types';
import { Box, Grid, Typography } from '@material-ui/core';
import { ALGORITHM_TYPE_TRANSLATIONS } from '../../constants/algorithms';
import { OPTIMIZATION_TYPES_TRANSLATIONS } from '../../constants/optimizations';

interface Props {
  path: PathResponse;
}

const PathSummary = ({ path }: Props) => {
  return (
    <Box mt={2}>
      <Grid>
        <Typography>
          Algorithm: {ALGORITHM_TYPE_TRANSLATIONS[path.algorithm]}
        </Typography>
        <Typography>
          Optimization: {OPTIMIZATION_TYPES_TRANSLATIONS[path.optimization]}
        </Typography>
        <Typography>Number of nodes: {path.totalNodes}</Typography>
        <Typography>
          Total distance: {path.totalDistance.toFixed(2)} km
        </Typography>
        <Typography>
          Total duration: {path.totalDuration.toFixed()} minutes
        </Typography>
        <Typography>Total nodes visited: {path.totalVisitedNodes}</Typography>
        <Typography>
          Execution duration: {path.executionDuration.toFixed(2)} seconds
        </Typography>
      </Grid>
    </Box>
  );
};

export default PathSummary;
