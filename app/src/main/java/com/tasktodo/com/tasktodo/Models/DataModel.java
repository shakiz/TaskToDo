package com.tasktodo.com.tasktodo.Models;

public class DataModel {
    private String id;
    private String present_address_street;
    private String present_address_city;
    private String weightStatus;
    private String number_of_requets;
    private String pledge_number;

    public DataModel(String id,String present_address_city,String present_address_street,String weightStatus,String number_of_requets,String pledge_number){
        this.id=id;
        this.present_address_city=present_address_city;
        this.present_address_street=present_address_street;
        this.weightStatus=weightStatus;
        this.number_of_requets=number_of_requets;
        this.pledge_number=pledge_number;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPresent_address_street() {
        return present_address_street;
    }

    public void setPresent_address_street(String present_address_street) {
        this.present_address_street = present_address_street;
    }

    public String getPresent_address_city() {
        return present_address_city;
    }

    public void setPresent_address_city(String present_address_city) {
        this.present_address_city = present_address_city;
    }

    public String getWeightStatus() {
        return weightStatus;
    }

    public void setWeightStatus(String weightStatus) {
        this.weightStatus = weightStatus;
    }

    public String getNumber_of_requets() {
        return number_of_requets;
    }

    public void setNumber_of_requets(String number_of_requets) {
        this.number_of_requets = number_of_requets;
    }

    public String getPledge_number() {
        return pledge_number;
    }

    public void setPledge_number(String pledge_number) {
        this.pledge_number = pledge_number;
    }
}
