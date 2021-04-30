export enum OptimizationTypes {
  DISTANCE = 'DISTANCE',
  TIME = 'TIME',
  NUMBER_OF_NODES = 'NUMBER_OF_NODES',
}

export const OPTIMIZATION_TYPES_TRANSLATIONS = {
  [OptimizationTypes.TIME]: 'Time',
  [OptimizationTypes.DISTANCE]: 'Distance',
  [OptimizationTypes.NUMBER_OF_NODES]: 'Number of nodes',
};
