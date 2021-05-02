[![Test](https://github.com/Nalhin/Navigation/actions/workflows/test.yml/badge.svg?branch=main)](https://github.com/Nalhin/Navigation/actions/workflows/test.yml)
[![codecov](https://codecov.io/gh/Nalhin/Navigation/branch/main/graph/badge.svg)](https://codecov.io/gh/Nalhin/Navigation)
[![License](https://img.shields.io/github/license/nalhin/Movies)](LICENSE.md)

# Navigation

Real time navigation based on OSM data and pathfinding pathfindingAlgorithms

## Architecture

<p align="center">
    <img src="architecture/architecture.png" alt="architecture"/>
</p>

### OSM download url

http://download.geofabrik.de/europe/poland.html

## Modules

```
modules
  services 
    reverse-geocoding-api
    geocoding-api
    pathfinding-api
    osm-data-exporter
  core
    parser
    pathfinder

```