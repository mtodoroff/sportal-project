package com.sportal.exceptions;

public class NotFoundCategory extends RuntimeException{
    public NotFoundCategory(String msg){
        super(msg);
    }
}
