import React from 'react';
import DrawerContent from './drawer-content';
import {
  Box,
  Drawer as MaterialDrawer,
  Grid,
  IconButton,
} from '@material-ui/core';
import { css } from '@emotion/css';
import { Close, Settings } from '@material-ui/icons';
import PathfindingSettings from '../pathfinding/pathfinding-settings';

interface Props {
  isOpen: boolean;
  onClose: () => void;
}

const Drawer = ({ isOpen, onClose }: Props) => {
  const [isSettingsOpen, setSettingsOpen] = React.useState(false);

  return (
    <MaterialDrawer
      anchor={'left'}
      open={isOpen}
      onClose={onClose}
      className={css`
        width: 300px;
        flex-shrink: 0;
      `}
      classes={{
        paper: css`
          width: 300px;
        `,
      }}
      variant="persistent"
      ModalProps={{
        keepMounted: true,
      }}
    >
      <PathfindingSettings
        isOpen={isSettingsOpen}
        onClose={() => setSettingsOpen(false)}
      />
      <Box>
        <Grid container justify="flex-end">
          <IconButton onClick={() => setSettingsOpen(true)}>
            <Settings />
          </IconButton>
          <IconButton onClick={onClose}>
            <Close />
          </IconButton>
        </Grid>
        <DrawerContent />
      </Box>
    </MaterialDrawer>
  );
};

export default Drawer;
