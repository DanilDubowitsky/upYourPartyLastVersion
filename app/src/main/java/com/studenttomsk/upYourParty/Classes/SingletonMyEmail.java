package com.studenttomsk.upYourParty.Classes;

public class SingletonMyEmail {

        private static SingletonMyEmail instance;
        private String email;
        public void clearEmail(){
            email = "";
        }
        public void setEmail(String token){
            this.email = token;
        }
        public String getEmail(){
            return email;
        }
        public static synchronized SingletonMyEmail getInstance(){
            if(instance == null){
                instance = new SingletonMyEmail();
            }
            return instance;
        }

}
