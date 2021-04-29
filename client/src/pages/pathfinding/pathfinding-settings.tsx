import React from 'react';
import {
  Box,
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  FormControl,
  Grid,
  InputLabel,
  MenuItem,
  Select,
} from '@material-ui/core';
import {
  AlgorithmTypes,
  OptimizationTypes,
  usePathfindingSettings,
  useSetPathfindingSettings,
} from '../../context/pathfinding-settings/pathfinding-settings-context';

interface Props {
  isOpen: boolean;
  onClose: () => void;
}

const SETTINGS = {
  optimization: [
    { label: 'Distance', value: OptimizationTypes.DISTANCE },
    { label: 'Time', value: OptimizationTypes.TIME },
    { label: 'Number of nodes', value: OptimizationTypes.NUMBER_OF_NODES },
  ],
  algorithms: [
    { label: 'BFS', value: AlgorithmTypes.BFS },
    { label: 'Dijkstra', value: AlgorithmTypes.DIJKSTRA },
    { label: 'A*', value: AlgorithmTypes.A_STAR },
    { label: 'Bellman-Ford', value: AlgorithmTypes.BELLMAN_FORD },
  ],
};

const PathfindingSettings = ({ isOpen, onClose }: Props) => {
  const settings = usePathfindingSettings();
  const setSetting = useSetPathfindingSettings();

  return (
    <Dialog onClose={onClose} open={isOpen}>
      <DialogTitle>Settings</DialogTitle>
      <DialogContent dividers>
        <Box minWidth={300}>
          <Grid direction="column" container>
            <FormControl>
              <InputLabel>Optimization</InputLabel>
              <Select
                value={settings.optimization}
                onChange={(e) =>
                  setSetting.setOptimization(
                    e.target.value as OptimizationTypes,
                  )
                }
              >
                {SETTINGS.optimization.map((item) => (
                  <MenuItem value={item.value} key={item.value}>
                    {item.label}
                  </MenuItem>
                ))}
              </Select>
            </FormControl>
            <FormControl>
              <InputLabel>Algorithm</InputLabel>
              <Select
                value={settings.algorithm}
                onChange={(e) =>
                  setSetting.setAlgorithm(e.target.value as AlgorithmTypes)
                }
              >
                {SETTINGS.algorithms.map((item) => (
                  <MenuItem value={item.value} key={item.value}>
                    {item.label}
                  </MenuItem>
                ))}
              </Select>
            </FormControl>
          </Grid>
        </Box>
      </DialogContent>
      <DialogActions>
        <Button autoFocus onClick={onClose} color="primary">
          Save changes
        </Button>
      </DialogActions>
    </Dialog>
  );
};

export default PathfindingSettings;
