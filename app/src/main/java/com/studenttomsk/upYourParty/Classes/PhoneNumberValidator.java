package com.studenttomsk.upYourParty.Classes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneNumberValidator {
    private Pattern pattern;
    private Matcher matcher;
    private static final String PHONE_VALID = "^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$";

    public PhoneNumberValidator() {
        pattern = Pattern.compile(PHONE_VALID);
    }

    public boolean validate(final String number){
        matcher = pattern.matcher(number);
        return matcher.matches();
    }
}
