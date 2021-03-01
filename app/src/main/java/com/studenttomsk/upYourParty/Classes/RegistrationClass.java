package com.studenttomsk.upYourParty.Classes;

import com.studenttomsk.upYourParty.Classes.ProfileClass;

public class RegistrationClass {
    String email;
    String password;
    ProfileClass profilePerson;

    public RegistrationClass(String email, String password, ProfileClass profile) {
        this.email = email;
        this.password = password;
        this.profilePerson = profile;
    }
    public RegistrationClass(){

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
