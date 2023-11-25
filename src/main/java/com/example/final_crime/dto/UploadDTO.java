package com.example.final_crime.dto;

import java.sql.Timestamp;

public class UploadDTO {
    private double lat;
    private double lng;
    private Timestamp timestamp;

    public UploadDTO() {
    }

    public UploadDTO(double lat, double lng, Timestamp timestamp) {
        this.lat = lat;
        this.lng = lng;
        this.timestamp = timestamp;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
