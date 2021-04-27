import React from 'react';
import {
  DragDropContext,
  Draggable,
  Droppable,
  DropResult,
} from 'react-beautiful-dnd';
import { ListItem } from '../list-item.type';
import DraggableListItem from './draggable-list-item';

interface Props {
  setItems: (items: ListItem[]) => void;
  items: ListItem[];
}

const reorder = (list: any[], startIndex: number, endIndex: number) => {
  const result = [...list];
  const [removed] = result.splice(startIndex, 1);
  result.splice(endIndex, 0, removed);

  return result;
};

const DraggableList = ({ items, setItems }: Props) => {
  const onDragEnd = (result: DropResult) => {
    if (!result.destination) {
      return;
    }

    const itemsReordered = reorder(
      items,
      result.source.index,
      result.destination.index,
    );

    setItems(itemsReordered);
  };

  return (
    <div>
      <h1>Start</h1>
      <DragDropContext onDragEnd={onDragEnd}>
        <Droppable droppableId="droppable">
          {(provided) => (
            <div {...provided.droppableProps} ref={provided.innerRef}>
              {items.map((item, index) => (
                <Draggable
                  key={item.id}
                  draggableId={String(item.id)}
                  index={index}
                >
                  {(provided) => (
                    <div
                      ref={provided.innerRef}
                      {...provided.draggableProps}
                      {...provided.dragHandleProps}
                    >
                      <DraggableListItem item={item} />
                    </div>
                  )}
                </Draggable>
              ))}
              {provided.placeholder}
            </div>
          )}
        </Droppable>
      </DragDropContext>
    </div>
  );
};

export default DraggableList;
