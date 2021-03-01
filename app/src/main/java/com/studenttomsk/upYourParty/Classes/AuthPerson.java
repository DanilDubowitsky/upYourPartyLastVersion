package com.studenttomsk.upYourParty.Classes;

public class AuthPerson {
    private String email;
    private ProfileClass profilePerson;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ProfileClass getProfilePerson() {
        return profilePerson;
    }

    public void setProfilePerson(ProfileClass profilePerson) {
        this.profilePerson = profilePerson;
    }

    public AuthPerson(String email, ProfileClass profilePerson) {
        this.email = email;
        this.profilePerson = profilePerson;
    }
}
