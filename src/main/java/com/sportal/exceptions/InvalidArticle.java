package com.sportal.exceptions;

public class InvalidArticle extends RuntimeException{
    public InvalidArticle(String msg){
        super(msg);
    }
}
