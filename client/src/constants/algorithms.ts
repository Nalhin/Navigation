export enum AlgorithmTypes {
  DIJKSTRA = 'DIJKSTRA',
  A_STAR = 'A_STAR',
  BIDIRECTIONAL_A_STAR = 'BIDIRECTIONAL_A_STAR',
  BELLMAN_FORD = 'BELLMAN_FORD',
  BFS = 'BFS',
  BIDIRECTIONAL_BFS = 'BIDIRECTIONAL_BFS',
  BIDIRECTIONAL_DIJKSTRA = 'BIDIRECTIONAL_DIJKSTRA',
  DFS = 'DFS',
  GREEDY_BEST_FIRST_SEARCH = 'GREEDY_BEST_FIRST_SEARCH',
  BIDIRECTIONAL_GREEDY_BEST_FIRST_SEARCH = 'BIDIRECTIONAL_GREEDY_BEST_FIRST_SEARCH',
}

export const ALGORITHM_TYPE_TRANSLATIONS = {
  [AlgorithmTypes.DIJKSTRA]: 'Dijkstra',
  [AlgorithmTypes.BFS]: 'BFS',
  [AlgorithmTypes.BIDIRECTIONAL_BFS]: 'Bidirectional BFS',
  [AlgorithmTypes.BELLMAN_FORD]: 'Bellman-Ford',
  [AlgorithmTypes.A_STAR]: 'A*',
  [AlgorithmTypes.BIDIRECTIONAL_A_STAR]: 'Bidirectional A*',
  [AlgorithmTypes.BIDIRECTIONAL_DIJKSTRA]: 'Bidirectional Dijkstra',
  [AlgorithmTypes.DFS]: 'DFS',
  [AlgorithmTypes.GREEDY_BEST_FIRST_SEARCH]: 'Greedy Best First Search',
  [AlgorithmTypes.BIDIRECTIONAL_GREEDY_BEST_FIRST_SEARCH]:
    'Bidirectional Greedy Best First Search',
};
