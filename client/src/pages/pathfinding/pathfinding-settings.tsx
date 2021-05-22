import React from 'react';
import {
  Box,
  Button,
  Checkbox,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  FormControl,
  FormControlLabel,
  Grid,
  InputLabel,
  MenuItem,
  Select,
  TextField,
} from '@material-ui/core';
import {
  PathfindingSettingsContextProps,
  usePathfindingSettings,
  useSetPathfindingSettings,
} from '../../context/pathfinding-settings/pathfinding-settings-context';
import { Controller, useForm } from 'react-hook-form';
import { Bounds } from '../../api/requests/pathfinding/pathfinding.types';
import {
  OPTIMIZATION_TYPES_TRANSLATIONS,
  OptimizationTypes,
} from '../../constants/optimizations';
import {
  ALGORITHM_TYPE_TRANSLATIONS,
  AlgorithmTypes,
} from '../../constants/algorithms';

interface Props {
  isOpen: boolean;
  onClose: () => void;
}

const SETTINGS = {
  optimization: [
    OptimizationTypes.DISTANCE,
    OptimizationTypes.TIME,
    OptimizationTypes.NUMBER_OF_NODES,
    OptimizationTypes.NONE,
  ],
  algorithms: [
    AlgorithmTypes.BFS,
    AlgorithmTypes.BIDIRECTIONAL_BFS,
    AlgorithmTypes.DIJKSTRA,
    AlgorithmTypes.BIDIRECTIONAL_DIJKSTRA,
    AlgorithmTypes.A_STAR,
    AlgorithmTypes.BIDIRECTIONAL_A_STAR,
    AlgorithmTypes.BELLMAN_FORD,
    AlgorithmTypes.DFS,
    AlgorithmTypes.GREEDY_BEST_FIRST_SEARCH,
    AlgorithmTypes.BIDIRECTIONAL_GREEDY_BEST_FIRST_SEARCH,
  ],
};

interface FormState {
  optimization: OptimizationTypes;
  algorithm: AlgorithmTypes;
  bounded: boolean;
  bounds: Bounds;
}

const PathfindingSettings = ({ isOpen, onClose }: Props) => {
  const settings = usePathfindingSettings();
  const { setSettings } = useSetPathfindingSettings();
  const { handleSubmit, control } = useForm<FormState>({
    defaultValues: {
      optimization: settings.optimization,
      algorithm: settings.algorithm,
      bounded: settings.bounded,
      bounds: settings.bounds,
    },
  });

  const onSubmit = (values: PathfindingSettingsContextProps) => {
    setSettings(values);
  };

  return (
    <Dialog onClose={onClose} open={isOpen}>
      <DialogTitle>Settings</DialogTitle>
      <DialogContent dividers>
        <Box minWidth={300}>
          <Grid
            direction="column"
            container
            component="form"
            id="settings-form"
            onSubmit={handleSubmit(onSubmit)}
          >
            <FormControl>
              <InputLabel>Algorithm</InputLabel>
              <Controller
                render={({ field }) => (
                  <Select {...field}>
                    {SETTINGS.algorithms.map((item) => (
                      <MenuItem value={item} key={item}>
                        {ALGORITHM_TYPE_TRANSLATIONS[item]}
                      </MenuItem>
                    ))}
                  </Select>
                )}
                name="algorithm"
                control={control}
              />
            </FormControl>
            <FormControl>
              <InputLabel>Optimization</InputLabel>
              <Controller
                render={({ field }) => (
                  <Select {...field}>
                    {SETTINGS.optimization.map((item) => (
                      <MenuItem value={item} key={item}>
                        {OPTIMIZATION_TYPES_TRANSLATIONS[item]}
                      </MenuItem>
                    ))}
                  </Select>
                )}
                name="optimization"
                control={control}
              />
            </FormControl>
            <Box mt={1}>
              <FormControlLabel
                control={
                  <Controller
                    render={({ field }) => (
                      <Checkbox
                        {...field}
                        checked={field.value}
                        onChange={(e) => field.onChange(e.target.checked)}
                      />
                    )}
                    name="bounded"
                    control={control}
                  />
                }
                label="Bounded?"
              />
            </Box>
            <FormControl>
              <Controller
                render={({ field }) => (
                  <TextField label="Max Longitude" {...field} />
                )}
                name="bounds.maxLongitude"
                control={control}
              />
            </FormControl>
            <FormControl>
              <Controller
                render={({ field }) => (
                  <TextField label="Max Latitude" {...field} />
                )}
                name="bounds.maxLatitude"
                control={control}
              />
            </FormControl>
            <FormControl>
              <Controller
                render={({ field }) => (
                  <TextField label="Min latitude" {...field} />
                )}
                name="bounds.minLatitude"
                control={control}
              />
            </FormControl>
            <FormControl>
              <Controller
                render={({ field }) => (
                  <TextField label="Min longitude" {...field} />
                )}
                name="bounds.minLongitude"
                control={control}
              />
            </FormControl>
          </Grid>
        </Box>
      </DialogContent>
      <DialogActions>
        <Button
          autoFocus
          onClick={onClose}
          color="primary"
          type="submit"
          form="settings-form"
        >
          Save changes
        </Button>
      </DialogActions>
    </Dialog>
  );
};

export default PathfindingSettings;
