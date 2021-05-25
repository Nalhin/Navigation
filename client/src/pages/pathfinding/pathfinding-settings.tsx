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
import { getAvailableOptimizationsForAlgorithm } from '../../api/requests/pathfinding/pathfinding';
import { useQuery } from 'react-query';
import * as yup from 'yup';
import { yupResolver } from '@hookform/resolvers/yup';

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
    AlgorithmTypes.DFS,
    AlgorithmTypes.BFS,
    AlgorithmTypes.BIDIRECTIONAL_BFS,
    AlgorithmTypes.DIJKSTRA,
    AlgorithmTypes.BIDIRECTIONAL_DIJKSTRA,
    AlgorithmTypes.A_STAR,
    AlgorithmTypes.BIDIRECTIONAL_A_STAR,
    AlgorithmTypes.BELLMAN_FORD,
    AlgorithmTypes.GREEDY_BEST_FIRST_SEARCH,
    AlgorithmTypes.BIDIRECTIONAL_GREEDY_BEST_FIRST_SEARCH,
  ],
};

const schema = yup.object().shape({
  optimization: yup.string().oneOf(Object.values(OptimizationTypes)).required(),
  algorithm: yup.string().oneOf(Object.values(AlgorithmTypes)).required(),
  bounded: yup.bool().required(),
  bounds: yup.object().required().shape({
    minLatitude: yup.number().required(),
    maxLatitude: yup.number().required(),
    minLongitude: yup.number().required(),
    maxLongitude: yup.number().required(),
  }),
});

interface FormState {
  optimization: OptimizationTypes;
  algorithm: AlgorithmTypes;
  bounded: boolean;
  bounds: Bounds;
}

const PathfindingSettings = ({ isOpen, onClose }: Props) => {
  const settings = usePathfindingSettings();
  const { setSettings } = useSetPathfindingSettings();
  const {
    handleSubmit,
    control,
    watch,
    setValue,
    formState,
  } = useForm<FormState>({
    resolver: yupResolver(schema),
    defaultValues: {
      optimization: settings.optimization,
      algorithm: settings.algorithm,
      bounded: settings.bounded,
      bounds: settings.bounds,
    },
  });

  const selectedAlgorithm = watch('algorithm');
  React.useEffect(() => {
    if (formState.dirtyFields.algorithm) {
      setValue('optimization', '' as OptimizationTypes);
    }
  }, [selectedAlgorithm]);

  const { data = [], isLoading } = useQuery(
    ['availableOptimizations', selectedAlgorithm],
    () => getAvailableOptimizationsForAlgorithm(selectedAlgorithm),
    { select: (response) => response.data },
  );

  const onSubmit = (values: PathfindingSettingsContextProps) => {
    setSettings(values);
    onClose();
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
                  <Select {...field} error={!!formState.errors.algorithm}>
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
                  <Select
                    {...field}
                    disabled={isLoading}
                    error={!!formState.errors.optimization}
                  >
                    {SETTINGS.optimization.map((item) => (
                      <MenuItem
                        value={item}
                        key={item}
                        disabled={!data.includes(item)}
                      >
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
                  <TextField
                    label="Max Latitude"
                    {...field}
                    error={!!formState.errors.bounds?.maxLatitude}
                  />
                )}
                name="bounds.maxLatitude"
                control={control}
              />
            </FormControl>
            <FormControl>
              <Controller
                render={({ field }) => (
                  <TextField
                    label="Max Longitude"
                    {...field}
                    error={!!formState.errors.bounds?.maxLongitude}
                  />
                )}
                name="bounds.maxLongitude"
                control={control}
              />
            </FormControl>
            <FormControl>
              <Controller
                render={({ field }) => (
                  <TextField
                    label="Min latitude"
                    {...field}
                    error={!!formState.errors.bounds?.minLatitude}
                  />
                )}
                name="bounds.minLatitude"
                control={control}
              />
            </FormControl>
            <FormControl>
              <Controller
                render={({ field }) => (
                  <TextField
                    label="Min longitude"
                    {...field}
                    error={!!formState.errors.bounds?.minLongitude}
                  />
                )}
                name="bounds.minLongitude"
                control={control}
              />
            </FormControl>
          </Grid>
        </Box>
      </DialogContent>
      <DialogActions>
        <Button autoFocus color="primary" type="submit" form="settings-form">
          Save changes
        </Button>
      </DialogActions>
    </Dialog>
  );
};

export default PathfindingSettings;
