import React from 'react';
import { ListItem } from '../list-item.type';
import { Grid, TextField, Typography } from '@material-ui/core';
import LocationOnIcon from '@material-ui/icons/LocationOn';
import { useQuery } from 'react-query';
import { getGeocode } from '../../api/requests/geocode/geocode.requests';
import Autocomplete from '@material-ui/lab/Autocomplete';
import { css } from '@emotion/css';

interface Props {
  onValueSet: (item: ListItem | null) => void;
}

const Search = ({ onValueSet }: Props) => {
  const [value, setValue] = React.useState<ListItem | null>(null);
  const [inputValue, setInputValue] = React.useState('');
  const [options, setOptions] = React.useState<ListItem[]>([]);

  useQuery(['search', inputValue], () => getGeocode(inputValue), {
    onSuccess: (data) => setOptions(data.data),
    enabled: !!inputValue,
  });

  return (
    <Autocomplete
      className={css`
        width: 260px;
      `}
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
        onValueSet(newValue);
      }}
      onInputChange={(event, newInputValue) => {
        setInputValue(newInputValue);
      }}
      renderInput={(params) => (
        <TextField {...params} label="Find a location" fullWidth />
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
