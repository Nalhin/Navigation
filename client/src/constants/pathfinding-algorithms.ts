export enum PathfindingAlgorithmTypes {
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
  [PathfindingAlgorithmTypes.DIJKSTRA]: 'Dijkstra',
  [PathfindingAlgorithmTypes.BFS]: 'BFS',
  [PathfindingAlgorithmTypes.BIDIRECTIONAL_BFS]: 'Bidirectional BFS',
  [PathfindingAlgorithmTypes.BELLMAN_FORD]: 'Bellman-Ford',
  [PathfindingAlgorithmTypes.A_STAR]: 'A*',
  [PathfindingAlgorithmTypes.BIDIRECTIONAL_A_STAR]: 'Bidirectional A*',
  [PathfindingAlgorithmTypes.BIDIRECTIONAL_DIJKSTRA]: 'Bidirectional Dijkstra',
  [PathfindingAlgorithmTypes.DFS]: 'DFS',
  [PathfindingAlgorithmTypes.GREEDY_BEST_FIRST_SEARCH]:
    'Greedy Best First Search',
  [PathfindingAlgorithmTypes.BIDIRECTIONAL_GREEDY_BEST_FIRST_SEARCH]:
    'Bidirectional Greedy Best First Search',
};
