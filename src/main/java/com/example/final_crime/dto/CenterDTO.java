package com.example.final_crime.dto;

public class CenterDTO {
    private int sequence;
    private String agency;
    private String police_office;
    private String government_office_name;
    private String division;
    private String center_name;
    private String address;

    public CenterDTO() {
    }

    public CenterDTO(int sequence, String agency, String police_office, String government_office_name, String division, String center_name, String adress) {
        this.sequence = sequence;
        this.agency = agency;
        this.police_office = police_office;
        this.government_office_name = government_office_name;
        this.division = division;
        this.center_name = center_name;
        this.address = adress;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
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

    public void setAdress(String adress) {
        this.address = adress;
    }
}
