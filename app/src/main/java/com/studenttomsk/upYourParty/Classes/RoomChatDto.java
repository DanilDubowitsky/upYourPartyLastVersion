package com.studenttomsk.upYourParty.Classes;

public class RoomChatDto {
    private String email_to;

    public RoomChatDto(String email_to) {
        this.email_to = email_to;
    }

    public String getEmail_to() {
        return email_to;
    }

    public void setEmail_to(String email_to) {
        this.email_to = email_to;
    }
}
