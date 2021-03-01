package com.studenttomsk.upYourParty.Classes;

public class ChangeProfileClass {
    private String name;
    private String middle_name;
    private String number_phone;
    private String email;
    private String surname;
    private String city;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public ChangeProfileClass(String name, String middle_name, String number_phone, String email, String surname, String city) {
        this.name = name;
        this.middle_name = middle_name;
        this.number_phone = number_phone;
        this.email = email;
        this.surname = surname;
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public void setMiddle_name(String middle_name) {
        this.middle_name = middle_name;
    }

    public String getNumber_phone() {
        return number_phone;
    }

    public void setNumber_phone(String number_phone) {
        this.number_phone = number_phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
