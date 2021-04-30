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
  AlgorithmTypes,
  OptimizationTypes,
  PathfindingSettingsContextProps,
  usePathfindingSettings,
  useSetPathfindingSettings,
} from '../../context/pathfinding-settings/pathfinding-settings-context';
import { useForm, Controller } from 'react-hook-form';
import { Bounds } from '../../api/requests/pathfinding/pathfinding.types';

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
    { label: 'Bidirectional BFS', value: AlgorithmTypes.BIDIRECTIONAL_BFS },
    { label: 'Dijkstra', value: AlgorithmTypes.DIJKSTRA },
    { label: 'A*', value: AlgorithmTypes.A_STAR },
    { label: 'Bellman-Ford', value: AlgorithmTypes.BELLMAN_FORD },
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
    console.log(values);
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
              <InputLabel>Optimization</InputLabel>
              <Controller
                render={({ field }) => (
                  <Select {...field}>
                    {SETTINGS.optimization.map((item) => (
                      <MenuItem value={item.value} key={item.value}>
                        {item.label}
                      </MenuItem>
                    ))}
                  </Select>
                )}
                name="optimization"
                control={control}
              />
            </FormControl>
            <FormControl>
              <InputLabel>Algorithm</InputLabel>
              <Controller
                render={({ field }) => (
                  <Select {...field}>
                    {SETTINGS.algorithms.map((item) => (
                      <MenuItem value={item.value} key={item.value}>
                        {item.label}
                      </MenuItem>
                    ))}
                  </Select>
                )}
                name="algorithm"
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
