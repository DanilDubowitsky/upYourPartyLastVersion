package com.studenttomsk.upYourParty.Classes;

public class ProfileNumbNameTel {
    private String email;
    private String name;
    private String middle_name;
    private String surname;
    private String city;
    private String middleName;
    private String numberPhone;
    private String avatar;

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



    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public ProfileNumbNameTel(String email, String name, String middle_name, String numberPhone, String middleName, String avatar, String surname) {
        this.email = email;
        this.name = name;
        this.middle_name = middle_name;
        this.numberPhone = numberPhone;
        this.middleName = middleName;
        this.avatar = avatar;
        this.surname = surname;
    }
}
