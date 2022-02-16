package com.sportal.util;

import com.sportal.exceptions.BadRequestException;

public class Validator {
    //At least 8 symbols , 1 letter and 1 number
    //TODO if necessary stronger pattern for password
    private static final String PASSWORD_PATTERN = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
    private static final String EMAIL_PATTERN = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$";


    public static void validateEmail(String email){
        if(email == null || email.isEmpty()){
            throw new NullPointerException("Email cannot be empty");
        }
        if (!email.matches(EMAIL_PATTERN)) {
            throw new BadRequestException("Email is not valid format");
        }
    }

    public static void passwordTemplateValidator(String password){
        if (!password.matches(PASSWORD_PATTERN)) {
            throw new BadRequestException("Password is not in valid format. " +
                    "Make sure your password contains at least 8 symbols,1 letter and 1 number");
        }
    }

    public static void validatePassword(String password) {
        if(password == null || password.isEmpty()){
            throw new BadRequestException("Password cannot be empty");
        }
//        passwordTemplateValidator(password);
    }

    public static void validateUsername(String username){
        if(username == null || username.isEmpty()){
            throw new BadRequestException("Username cannot be empty");
        }
        username = username.trim();
        if(username.length() < 2){
            throw new BadRequestException("Username too short");
        }
    }
}
