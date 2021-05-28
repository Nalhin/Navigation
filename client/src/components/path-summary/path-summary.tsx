import React from 'react';
import { PathResponse } from '../../api/requests/pathfinding/pathfinding.types';
import { Box, Grid, Typography } from '@material-ui/core';
import { ALGORITHM_TYPE_TRANSLATIONS } from '../../constants/pathfinding-algorithms';
import { OPTIMIZATION_TYPES_TRANSLATIONS } from '../../constants/pathfinding-optimizations';
import styled from '@emotion/styled';

const StyledKey = styled.span`
  font-weight: 500;
`;

interface Props {
  path: PathResponse;
}

const PathSummary = ({ path }: Props) => {
  return (
    <Box mt={2}>
      <Grid>
        <Typography>
          <StyledKey>Algorithm: </StyledKey>
          {ALGORITHM_TYPE_TRANSLATIONS[path.algorithm]}
        </Typography>
        <Typography>
          <StyledKey>Optimization: </StyledKey>
          {OPTIMIZATION_TYPES_TRANSLATIONS[path.optimization]}
        </Typography>
        <Typography>
          <StyledKey>Number of nodes: </StyledKey> {path.totalNodes}
        </Typography>
        <Typography>
          <StyledKey>Total distance: </StyledKey>
          {path.totalDistance.toFixed(2)}
          km
        </Typography>
        <Typography>
          <StyledKey>Total duration: </StyledKey>
          {path.totalDuration.toFixed()} minutes
        </Typography>
        <Typography>
          <StyledKey>Total nodes visited: </StyledKey> {path.totalVisitedNodes}
        </Typography>
        <Typography>
          <StyledKey>Execution duration: </StyledKey>
          {path.executionDuration.toFixed(2)} seconds
        </Typography>
        <Typography>
          <StyledKey>Result: </StyledKey>
          {path.found ? 'Found' : 'Not found'}
        </Typography>
      </Grid>
    </Box>
  );
};

export default PathSummary;
