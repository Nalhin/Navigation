import React from 'react';
import { PathResponse } from '../../api/requests/pathfinding/pathfinding.types';
import { Grid, Typography } from '@material-ui/core';

interface Props {
  path: PathResponse;
}

const PathSummary = ({ path }: Props) => {
  return (
    <Grid>
      <Typography>Number of nodes: {path.points.length}</Typography>
    </Grid>
  );
};

export default PathSummary;
