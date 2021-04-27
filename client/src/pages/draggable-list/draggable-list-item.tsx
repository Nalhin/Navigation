import React from 'react';
import { ListItem } from '../list-item.type';
import { Box } from '@chakra-ui/react';

interface Props {
  item: ListItem;
}

const DraggableListItem = ({ item }: Props) => {
  return (
    <Box maxW="sm" borderWidth="1px" borderRadius="lg" overflow="hidden">
      <Box>{item.city}</Box>
      <Box>
        {item.street} {item.houseNumber}
      </Box>
    </Box>
  );
};

export default DraggableListItem;
