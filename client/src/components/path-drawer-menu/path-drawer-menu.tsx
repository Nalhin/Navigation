import React from 'react';
import PathDrawerMenuContent from './path-drawer-menu-content';
import {
  Box,
  Drawer as MaterialDrawer,
  Grid,
  IconButton,
} from '@material-ui/core';
import { css } from '@emotion/css';
import { Close, Settings } from '@material-ui/icons';
import PathfindingSettingsModal from '../pathfinding-settings-modal/pathfinding-settings-modal';

interface Props {
  isOpen: boolean;
  onClose: () => void;
}

const PathDrawerMenu = ({ isOpen, onClose }: Props) => {
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
        keepMounted: false,
      }}
    >
      {isOpen && (
        <PathfindingSettingsModal
          isOpen={isSettingsOpen}
          onClose={() => setSettingsOpen(false)}
        />
      )}
      <Box>
        <Grid container justify="flex-end">
          <IconButton onClick={() => setSettingsOpen(true)}>
            <Settings />
          </IconButton>
          <IconButton onClick={onClose}>
            <Close />
          </IconButton>
        </Grid>
        {isOpen && <PathDrawerMenuContent />}
      </Box>
    </MaterialDrawer>
  );
};

export default PathDrawerMenu;
