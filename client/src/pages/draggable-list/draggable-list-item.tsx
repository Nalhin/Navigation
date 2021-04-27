import React from 'react';
import { ListItem } from '../list-item.type';
import { Box, Button } from '@chakra-ui/react';

interface Props {
  item: ListItem;
  index: number;
  onRemove: () => void;
}

const DraggableListItem = ({ index, item, onRemove }: Props) => {
  return (
    <Box maxW="sm" borderWidth="1px" borderRadius="lg" overflow="hidden" p={2}>
      <Box>{index}.</Box>
      <Box>
        {item.city} {item.postCode}
      </Box>
      <Box>
        {item.street} {item.houseNumber}
      </Box>
      <Button
        colorScheme="red"
        size="sm"
        onClick={(e) => {
          e.stopPropagation();
          onRemove();
        }}
      >
        Remove
      </Button>
    </Box>
  );
};

export default DraggableListItem;
