package com.project.rentmyboatapp.user;

import android.widget.EditText;

public class User {

    private String name;
    private String phonenumber;
    private String password;
    private String email;
    private String address;
    private String bank;

    public User() {
    }

    public User(String name, String phonenumber,String email, String address) {
        this.name = name;
        this.phonenumber = phonenumber;
        this.email = email;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress(String address) {return address; }

    public void setAddress(String address) {this.address = address; }

    public String getBank(String bank) {return bank; }

    public void setBank(String bank) {this.bank = bank;}


}
