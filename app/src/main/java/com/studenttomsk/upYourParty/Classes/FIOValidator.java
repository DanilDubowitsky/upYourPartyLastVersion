package com.studenttomsk.upYourParty.Classes;

import org.apache.commons.codec.language.bm.Rule;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FIOValidator {
    private Pattern pattern;
    private Matcher matcher;
    private static final String TEXT_PATTERN = "^[а-яА-Я]+$";
    public FIOValidator(){
        pattern = Pattern.compile(TEXT_PATTERN);
    }

    public boolean validate(final String text){
        matcher = pattern.matcher(text);
        return matcher.matches();
    }
}
