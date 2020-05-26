package com.echo.moviememoir.entity;

public class User {
    private Integer userId;
    private String name;
    private String surname;
    private boolean gender;
    private String address;
    private String state;
    private String postcode;
    private String dob;

    public User() {
    }

    public User(Integer userId, String name, String surname, boolean gender, String address, String state, String postcode, String dob) {
        this.userId = userId;
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.address = address;
        this.state = state;
        this.postcode = postcode;
        this.dob = dob;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", gender=" + gender +
                ", address='" + address + '\'' +
                ", state='" + state + '\'' +
                ", postcode='" + postcode + '\'' +
                ", dob='" + dob + '\'' +
                '}';
    }
}
