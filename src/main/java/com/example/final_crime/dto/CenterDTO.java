package com.example.final_crime.dto;

public class CenterDTO {
    private int sequence;

    private double lat;

    private double lng;
    private String agency;
    private String police_office;
    private String government_office_name;
    private String division;
    private String center_name;
    private String address;

    public CenterDTO() {
    }

    public CenterDTO(int sequence, double lat, double lng, String agency, String police_office, String government_office_name, String division, String center_name, String address) {
        this.sequence = sequence;
        this.lat = lat;
        this.lng = lng;
        this.agency = agency;
        this.police_office = police_office;
        this.government_office_name = government_office_name;
        this.division = division;
        this.center_name = center_name;
        this.address = address;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
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

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public String getPolice_office() {
        return police_office;
    }

    public void setPolice_office(String police_office) {
        this.police_office = police_office;
    }

    public String getGovernment_office_name() {
        return government_office_name;
    }

    public void setGovernment_office_name(String government_office_name) {
        this.government_office_name = government_office_name;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getCenter_name() {
        return center_name;
    }

    public void setCenter_name(String center_name) {
        this.center_name = center_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
