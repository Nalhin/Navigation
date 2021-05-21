[![Test](https://github.com/Nalhin/Navigation/actions/workflows/test.yml/badge.svg?branch=main)](https://github.com/Nalhin/Navigation/actions/workflows/test.yml)
[![codecov](https://codecov.io/gh/Nalhin/Navigation/branch/main/graph/badge.svg)](https://codecov.io/gh/Nalhin/Navigation)
[![License](https://img.shields.io/github/license/nalhin/Navigation)](LICENSE.md)

# Navigation

Navigation based on open source geospatial data and pathfinding algorithms.

## Table of contents
* [Description](#description)
* [Features](#features)
* [Presentation](#presentation)  
* [Architecture](#architecture)
* [Prerequisites](#prerequisites)
* [Setup](#setup)  
* [License](#license)

## Description

## Features

* Implementation and visualization of the following pathfinding algorithms:
  * [BFS](backend/libraries/pathfinder/src/main/java/com/navigation/pathfinder/pathfinding/BFSPathfindingStrategy.java) 
  * [Bidirectional BFS](backend/libraries/pathfinder/src/main/java/com/navigation/pathfinder/pathfinding/BidirectionalBFSPathfindingStrategy.java)
  * DFS  
  * [Bellman-Ford](backend/libraries/pathfinder/src/main/java/com/navigation/pathfinder/pathfinding/BellmanFordPathfindingStrategy.java)
  * [Dijkstra](backend/libraries/pathfinder/src/main/java/com/navigation/pathfinder/pathfinding/DijkstraPathfindingStrategy.java)
  * [Bidirectional Dijkstra](backend/libraries/pathfinder/src/main/java/com/navigation/pathfinder/pathfinding/BidirectionalDijkstraPathfindingStrategy.java)
  * [A*](backend/libraries/pathfinder/src/main/java/com/navigation/pathfinder/pathfinding/AStarPathfindingStrategy.java)
  * Bidirectional A*
* Geocoding - converting addresses to coordinates
* Reverse Geocoding - converting coordinates to addresses
* OSM file parser
* Data distribution pipeline in microservices architecture

## Presentation

## Architecture

<p align="center">
    <img src="architecture/architecture.png" alt="architecture"/>
</p>

### Modules

```
modules
  services 
    reverse-geocoding-api
    geocoding-api
    pathfinding-api
    osm-data-exporter
  libraries 
    parser
    pathfinder

```

## Prerequisites

## Setup

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.