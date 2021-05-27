import React from 'react';
import { AddressItem } from '../../types/address-item.type';
import { Grid, TextField, Typography } from '@material-ui/core';
import LocationOnIcon from '@material-ui/icons/LocationOn';
import { useQuery } from 'react-query';
import { getGeocode } from '../../api/requests/geocode/geocode.requests';
import Autocomplete from '@material-ui/lab/Autocomplete';
import { css } from '@emotion/css';
import { useDebounce } from 'use-debounce';

interface Props {
  onValueSelected: (item: AddressItem | null) => void;
  label: string;
  value: AddressItem | null;
}

const AddressAutocomplete = ({ onValueSelected, label, value }: Props) => {
  const [inputValue, setInputValue] = React.useState('');
  const [options, setOptions] = React.useState<AddressItem[]>([]);

  const [debounced] = useDebounce(inputValue, 250);

  const { isLoading } = useQuery(
    ['search', debounced],
    () => getGeocode(debounced),
    {
      onSuccess: (data) => setOptions(data.data),
      enabled: !!debounced,
    },
  );

  return (
    <Autocomplete
      className={css`
        width: 100%;
        padding-bottom: 8px;
      `}
      loading={isLoading}
      filterOptions={(x) => x}
      getOptionLabel={(option: AddressItem | string) =>
        typeof option === 'string'
          ? option
          : `${option.city ? option.city + ',' : ''}  ${option.street} ${
              option.houseNumber
            }`
      }
      options={
        value !== null && !options.find((o) => o === value)
          ? [...options, value as AddressItem]
          : options
      }
      autoComplete
      includeInputInList
      filterSelectedOptions
      value={value}
      onChange={(event: React.ChangeEvent<unknown>, newValue) => {
        setOptions(newValue ? [newValue, ...options] : options);
        onValueSelected(newValue);
      }}
      onInputChange={(event, newInputValue) => {
        setInputValue(newInputValue);
      }}
      renderInput={(params) => (
        <TextField {...params} label={label} fullWidth />
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

export default AddressAutocomplete;
