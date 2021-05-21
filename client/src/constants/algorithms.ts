export enum AlgorithmTypes {
  DIJKSTRA = 'DIJKSTRA',
  A_STAR = 'A_STAR',
  BELLMAN_FORD = 'BELLMAN_FORD',
  BFS = 'BFS',
  BIDIRECTIONAL_BFS = 'BIDIRECTIONAL_BFS',
  BIDIRECTIONAL_DIJKSTRA = 'BIDIRECTIONAL_DIJKSTRA',
}

export const ALGORITHM_TYPE_TRANSLATIONS = {
  [AlgorithmTypes.DIJKSTRA]: 'Dijkstra',
  [AlgorithmTypes.BFS]: 'BFS',
  [AlgorithmTypes.BIDIRECTIONAL_BFS]: 'Bidirectional BFS',
  [AlgorithmTypes.BELLMAN_FORD]: 'Bellman-Ford',
  [AlgorithmTypes.A_STAR]: 'A*',
  [AlgorithmTypes.BIDIRECTIONAL_DIJKSTRA]: 'Bidirectional Dijkstra',
};
