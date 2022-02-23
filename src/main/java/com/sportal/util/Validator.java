package com.sportal.util;

import com.sportal.exceptions.BadRequestException;

public class Validator {
    private static final String EMAIL_REGEX = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\\\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\\\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    private static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$";
//    private static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\\\S+$).{8,}$";

    public static void validateEmail(String email){
        if(email.isEmpty()){
            throw new BadRequestException("Email cannot be empty!");
        }
        if (!email.matches(EMAIL_REGEX)) {
            throw new BadRequestException("Email is not valid!");
        }
    }

    public static void passwordFormatValidator(String password){
        if (!password.matches(PASSWORD_REGEX)) {
            throw new BadRequestException("Password is not strong enough " +
                    "Please make sure your password contains at least 8 characters, " +
                    "one digit, one lower symbol and one upper symbol and special character");
        }
    }

    public static void validatePassword(String password) {
        if(password.isEmpty()){
            throw new BadRequestException("Password cannot be empty!");
        }
        passwordFormatValidator(password);
    }

    public static void validatePhone(String phone){
        if(phone.trim().isEmpty()){
            throw new BadRequestException("Phone cannot be empty!");
        }
        if(!phone.matches("\\d+")){
            throw new BadRequestException("Invalid phone number");
        }
        if (phone.trim().length() < 6){
            throw new BadRequestException("Phone must be at least 6 digits");
        }
    }

    public static  void validateEmptyField(String field,String fieldName){
        if(field.trim().isEmpty()){
            throw new BadRequestException(fieldName +" "+"cannot be empty!");
        }
    }
}
