package com.sportal.exceptions;

public class UnauthorizedException extends RuntimeException{
   public UnauthorizedException(String msg){
       super(msg);
   }
}
