package com.studenttomsk.upYourParty.Classes;

public class SingletonTitle {
        private static SingletonTitle instance;
        private String token;
        public void SetString(String token){
            this.token = token;
        }
        public String GetString(){
            return token;
        }
        public static synchronized SingletonTitle getInstance(){
            if(instance == null){
                instance = new SingletonTitle();
            }
            return instance;
        }



}
