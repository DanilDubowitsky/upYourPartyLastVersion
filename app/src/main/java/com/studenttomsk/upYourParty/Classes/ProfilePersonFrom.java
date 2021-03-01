package com.studenttomsk.upYourParty.Classes;

public class ProfilePersonFrom {
    private String email;
    private ProfileNumbNameTel profilePerson;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ProfileNumbNameTel getProfilePerson() {
        return profilePerson;
    }

    public void setProfilePerson(ProfileNumbNameTel profilePerson) {
        this.profilePerson = profilePerson;
    }

    public ProfilePersonFrom(String email, ProfileNumbNameTel profilePerson) {
        this.email = email;
        this.profilePerson = profilePerson;
    }
}
