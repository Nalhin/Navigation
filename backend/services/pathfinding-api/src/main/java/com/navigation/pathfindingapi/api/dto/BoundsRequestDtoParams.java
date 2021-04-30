package com.navigation.pathfindingapi.api.dto;

public class BoundsRequestDtoParams {

    private Double minLatitude;
    private Double minLongitude;
    private Double maxLatitude;
    private Double maxLongitude;

    public Double getMinLatitude() {
        return minLatitude;
    }

    public void setMinLatitude(Double minLatitude) {
        this.minLatitude = minLatitude;
    }

    public Double getMinLongitude() {
        return minLongitude;
    }

    public void setMinLongitude(Double minLongitude) {
        this.minLongitude = minLongitude;
    }

    public Double getMaxLatitude() {
        return maxLatitude;
    }

    public void setMaxLatitude(Double maxLatitude) {
        this.maxLatitude = maxLatitude;
    }

    public Double getMaxLongitude() {
        return maxLongitude;
    }

    public void setMaxLongitude(Double maxLongitude) {
        this.maxLongitude = maxLongitude;
    }
}
