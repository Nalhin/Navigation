import React from 'react';
import { AddressItem } from '../../types/address-item.type';
import {
  Button,
  Card,
  CardActions,
  CardContent,
  Divider,
  Typography,
} from '@material-ui/core';
import { usePathfinding } from '../../context/pathfinding-context/pathfinding-context';
import styled from '@emotion/styled';

const StyledCardActions = styled(CardActions)`
  display: flex;
  justify-content: space-between;
`;

interface Props {
  item: AddressItem;
}

const CurrentPoint = ({ item }: Props) => {
  const pathfinding = usePathfinding();

  return (
    <Card>
      <CardContent>
        <Typography variant="h5" component="h2">
          Location info
        </Typography>
        <Divider />
        <Typography variant="body2" component="p">
          {item.city ? `${item.city},` : ''} {item.country}
        </Typography>
        <Typography variant="body2" component="p">
          {item.street} {item.houseNumber}
        </Typography>
        <Typography variant="body2" component="p">
          Latitude: {item.location.latitude}, Longitude:{' '}
          {item.location.longitude}
        </Typography>
      </CardContent>
      <StyledCardActions>
        <Button size="small" onClick={() => pathfinding.setStart(item)}>
          Set start
        </Button>
        <Button size="small" onClick={() => pathfinding.setEnd(item)}>
          Set end
        </Button>
      </StyledCardActions>
    </Card>
  );
};

export default CurrentPoint;
