package com.project.rentmyboatapp.user;

public class User {

    private String name;
    private String driverlicense;
    private String password;
    private String email;

    public User() {
    }

    public User(String name, String driverlicense,String email) {
        this.name = name;
        this.driverlicense = driverlicense;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDriverlicense() {
        return driverlicense;
    }

    public void setDriverlicense(String driverlicense) {
        this.driverlicense = driverlicense;
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
}
