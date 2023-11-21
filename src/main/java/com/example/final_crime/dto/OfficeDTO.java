package com.example.final_crime.dto;


public class OfficeDTO {
    private double lat;
    private double lng;
    private String year;
    private String office;
    private String murder;
    private String robbery;
    private String theft;
    private String violence;
    private String address;

    public OfficeDTO() {
    }

    public OfficeDTO(double lat, double lng, String year, String office, String murder, String robbery, String theft, String violence, String address) {
        this.lat = lat;
        this.lng = lng;
        this.year = year;
        this.office = office;
        this.murder = murder;
        this.robbery = robbery;
        this.theft = theft;
        this.violence = violence;
        this.address = address;
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

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public String getMurder() {
        return murder;
    }

    public void setMurder(String murder) {
        this.murder = murder;
    }

    public String getRobbery() {
        return robbery;
    }

    public void setRobbery(String robbery) {
        this.robbery = robbery;
    }

    public String getTheft() {
        return theft;
    }

    public void setTheft(String theft) {
        this.theft = theft;
    }

    public String getViolence() {
        return violence;
    }

    public void setViolence(String violence) {
        this.violence = violence;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
