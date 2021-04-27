import React from 'react';
import { ListItem } from '../list-item.type';
import { Grid, TextField, Typography } from '@material-ui/core';
import LocationOnIcon from '@material-ui/icons/LocationOn';
import { useQuery } from 'react-query';
import { getGeocode } from '../../api/requests/geocode/geocode.requests';
import Autocomplete from '@material-ui/lab/Autocomplete';

const Search = () => {
  const [value, setValue] = React.useState<ListItem | null>(null);
  const [inputValue, setInputValue] = React.useState('');
  const [options, setOptions] = React.useState<ListItem[]>([]);

  useQuery(['search', inputValue], () => getGeocode(inputValue), {
    onSuccess: (data) => setOptions(data.data),
    enabled: !!inputValue,
  });

  return (
    <Autocomplete
      style={{ width: 300 }}
      filterOptions={(x) => x}
      getOptionLabel={(option: ListItem | string) =>
        typeof option === 'string'
          ? option
          : `${option.city}, ${option.street} ${option.houseNumber}`
      }
      options={options}
      autoComplete
      includeInputInList
      filterSelectedOptions
      value={value}
      onChange={(event: React.ChangeEvent<unknown>, newValue) => {
        setOptions(newValue ? [newValue, ...options] : options);
        setValue(newValue);
      }}
      onInputChange={(event, newInputValue) => {
        setInputValue(newInputValue);
      }}
      renderInput={(params) => (
        <TextField
          {...params}
          label="Add a location"
          variant="outlined"
          fullWidth
        />
      )}
      renderOption={(option) => {
        return (
          <Grid container alignItems="center">
            <Grid item>
              <LocationOnIcon />
            </Grid>
            <Grid item xs>
              <Typography variant="body2" color="textSecondary">
                {option.city}, {option.street} {option.houseNumber}
              </Typography>
            </Grid>
          </Grid>
        );
      }}
    />
  );
};

export default Search;
