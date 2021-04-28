import React from 'react';
import { ListItem } from './list-item.type';
import {
  Button,
  Card,
  CardActions,
  CardContent,
  Divider,
  Typography,
} from '@material-ui/core';

interface Props {
  item: ListItem;
}

const CurrentPoint = ({ item }: Props) => {
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
      </CardContent>
      <CardActions>
        <Button size="small">Learn More</Button>
      </CardActions>
    </Card>
  );
};

export default CurrentPoint;
