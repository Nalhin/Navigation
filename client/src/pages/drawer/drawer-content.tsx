import React from 'react';
import AddressSearch from '../address-search/address-search';
import { usePathfinding } from '../../context/pathfinding/pathfinding-context';
import { Box, Button, Typography } from '@material-ui/core';
import { css } from '@emotion/css';
import PathSummary from '../pathfinding/path-summary';

const DrawerContent = ({}) => {
  const pathfinding = usePathfinding();

  return (
    <Box p={2}>
      <Typography variant="h5" gutterBottom align="center">
        Path
      </Typography>
      <AddressSearch
        value={pathfinding.selectedPoints.start}
        label="Start"
        onValueSet={pathfinding.setStart}
      />
      <AddressSearch
        value={pathfinding.selectedPoints.end}
        label="End"
        onValueSet={pathfinding.setEnd}
      />
      <Button
        className={css`
          width: 100%;
          padding-top: 8px;
        `}
        variant="contained"
        color="primary"
        onClick={pathfinding.findPath}
      >
        Find
      </Button>
      {pathfinding.path && <PathSummary path={pathfinding.path} />}
    </Box>
  );
};

export default DrawerContent;
