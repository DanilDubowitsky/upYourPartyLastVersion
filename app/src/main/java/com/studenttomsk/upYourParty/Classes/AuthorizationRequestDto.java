package com.studenttomsk.upYourParty.Classes;

public class AuthorizationRequestDto {
    String email;
    String password;

    public AuthorizationRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
