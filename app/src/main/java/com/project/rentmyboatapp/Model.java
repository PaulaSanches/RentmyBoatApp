package com.project.rentmyboatapp;

public class Model {

    String id, name, address, bankdetails;
    public Model(){}


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBankdetails() {
        return bankdetails;
    }

    public void setBankdetails(String bankdetails) {
        this.bankdetails = bankdetails;
    }

    public Model(String id, String name, String bankdetails) {

            this.id=id;
            this.name = name;
            this. bankdetails = bankdetails;


        }
}
