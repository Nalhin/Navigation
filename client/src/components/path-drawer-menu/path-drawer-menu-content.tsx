import React from 'react';
import AddressAutocomplete from '../address-autocomplete/address-autocomplete';
import { usePathfinding } from '../../context/pathfinding-context/pathfinding-context';
import { Box, Button, IconButton, Typography } from '@material-ui/core';
import { css } from '@emotion/css';
import PathSummary from '../path-summary/path-summary';
import { useIsMutating } from 'react-query';
import SwapVertIcon from '@material-ui/icons/SwapVert';
import ClearAllIcon from '@material-ui/icons/ClearAll';

const PathDrawerMenuContent = ({}) => {
  const pathfinding = usePathfinding();
  const isMutating = useIsMutating({ mutationKey: 'path-between' });

  return (
    <Box p={2}>
      <Typography variant="h5" gutterBottom align="center">
        Path
      </Typography>
      <AddressAutocomplete
        value={pathfinding.selectedPoints.start}
        label="Start"
        onValueSelected={pathfinding.setStart}
      />
      <AddressAutocomplete
        value={pathfinding.selectedPoints.end}
        label="End"
        onValueSelected={pathfinding.setEnd}
      />
      <IconButton
        color="primary"
        aria-label="swap"
        onClick={pathfinding.swapStartAndEnd}
      >
        <SwapVertIcon />
      </IconButton>
      <IconButton
        color="primary"
        aria-label="clear all"
        onClick={pathfinding.clear}
      >
        <ClearAllIcon />
      </IconButton>
      <Button
        className={css`
          width: 100%;
          padding-top: 8px;
        `}
        variant="contained"
        color="primary"
        onClick={pathfinding.findPath}
        disabled={!!isMutating}
      >
        Find
      </Button>
      {pathfinding.path && <PathSummary path={pathfinding.path} />}
    </Box>
  );
};

export default PathDrawerMenuContent;
