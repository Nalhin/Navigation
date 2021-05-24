[![Test](https://github.com/Nalhin/Navigation/actions/workflows/test.yml/badge.svg?branch=main)](https://github.com/Nalhin/Navigation/actions/workflows/test.yml)
[![codecov](https://codecov.io/gh/Nalhin/Navigation/branch/main/graph/badge.svg)](https://codecov.io/gh/Nalhin/Navigation)
[![License](https://img.shields.io/github/license/nalhin/Navigation)](LICENSE.md)

# Navigation

Real-world navigation based on open source geospatial data and single-source shortest path algorithms

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

* OSM file parser
* OSM street network graph
* Implementation and visualization of the following single-source shortest path algorithms:
  * [BFS](backend/libraries/pathfinder/src/main/java/com/navigation/pathfinder/pathfinding/BFSPathfindingStrategy.java) 
  * [Bidirectional BFS](backend/libraries/pathfinder/src/main/java/com/navigation/pathfinder/pathfinding/BidirectionalBFSPathfindingStrategy.java)
  * [Bellman-Ford](backend/libraries/pathfinder/src/main/java/com/navigation/pathfinder/pathfinding/BellmanFordPathfindingStrategy.java)
  * [Dijkstra](backend/libraries/pathfinder/src/main/java/com/navigation/pathfinder/pathfinding/DijkstraPathfindingStrategy.java)
  * [Bidirectional Dijkstra](backend/libraries/pathfinder/src/main/java/com/navigation/pathfinder/pathfinding/BidirectionalDijkstraPathfindingStrategy.java)
  * [A*](backend/libraries/pathfinder/src/main/java/com/navigation/pathfinder/pathfinding/AStarPathfindingStrategy.java)
  * [Bidirectional A*](backend/libraries/pathfinder/src/main/java/com/navigation/pathfinder/pathfinding/BidirectionalAStarPathfindingStrategy.java)
* Implementation and visualization of the following pathfinding algorithms (with suboptimal results):
  * [DFS](backend/libraries/pathfinder/src/main/java/com/navigation/pathfinder/pathfinding/DFSPathfindingStrategy.java)
  * [Greedy Best First Search](backend/libraries/pathfinder/src/main/java/com/navigation/pathfinder/pathfinding/GreedyBestFirstSearchPathfindingStrategy.java)
  * [Bidirectional Greedy Best First Search](backend/libraries/pathfinder/src/main/java/com/navigation/pathfinder/pathfinding/BidirectionalGreedyBestFirstSearchPathfindingStrategy.java)
* Geocoding - converting addresses to coordinates
* Reverse Geocoding - converting coordinates to addresses
* Data distribution pipeline in microservice architecture
* Interactive visualization on street and satellite maps

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

Install [jdk16](https://www.oracle.com/java/technologies/javase-jdk16-downloads.html)
and [gradle](https://gradle.org/).

You should be able to run the following commands:
```bash
java --version
gradle --version
```

Install [docker](https://docs.docker.com/install/) and [docker-compose](https://docs.docker.com/compose/). 

You should be able to run the following commands:
```bash
docker --version
docker-compsoe --version
```

Install [node](https://nodejs.org/en), [npm](https://www.npmjs.com) and [yarn](https://yarnpkg.com). 

You should be able to run the following commands:
```bash
node --version
npm --version
yarn --version
```

## Setup

### Download map data

#### Preconfigured download script

```bash
bash download.sh
```

#### Manual download

Visit [geofabrik website](http://download.geofabrik.de/europe) and download any .osm.bzip of choice.
Rename the downloaded file to ``osm-data.osm.bz2`` and move it to the `data` directory.

### Build the application

#### Backend

```
cd backend
./gradlew clean build 
```

#### Client

```
cd client
yarn install
yarn build
```

### Run docker compose

```bash
cd docker
docker-compose -f docker-compose.prod.yml up -d
sleep 30
bash init-prod.sh
```

### Wait for export

Wait for the export process to finish (osm-data-processor should shut itself down).
This process should take approximately 3-5 min per 100Mb of compressed map data.

### Open client

Open [localhost](http://localhost:3000) in your browser.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.