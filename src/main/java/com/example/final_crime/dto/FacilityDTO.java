package com.example.final_crime.dto;

public class FacilityDTO {

    private double lat;
    private double lng;
    private String location_1;
    private String location_2;
    private int facility_code;
    private String name;
    private String phone;

    public FacilityDTO() {
    }

    public FacilityDTO(double lat, double lng, String location_1, String location_2, int facility_code, String name, String phone) {
        this.lat = lat;
        this.lng = lng;
        this.location_1 = location_1;
        this.location_2 = location_2;
        this.facility_code = facility_code;
        this.name = name;
        this.phone = phone;
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

    public String getLocation_1() {
        return location_1;
    }

    public void setLocation_1(String location_1) {
        this.location_1 = location_1;
    }

    public String getLocation_2() {
        return location_2;
    }

    public void setLocation_2(String location_2) {
        this.location_2 = location_2;
    }

    public int getFacility_code() {
        return facility_code;
    }

    public void setFacility_code(int facility_code) {
        this.facility_code = facility_code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
