package com.studenttomsk.upYourParty.Classes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator {
    private Pattern pattern;
    private Matcher matcher;
    private static final String PASSWORD_PATTERN =
            "^[a-zA-Z0-9]+$";

    public PasswordValidator() {
        pattern = Pattern.compile(PASSWORD_PATTERN);
    }
    public boolean validate(final String pass){
        matcher = pattern.matcher(pass);
        return matcher.matches();
    }

}
