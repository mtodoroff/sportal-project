package com.sportal.controller;

import com.sportal.exceptions.InvalidArticle;
import com.sportal.exceptions.UnauthorizedException;
import com.sportal.model.dto.userDTO.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomHandlerException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {UnauthorizedException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ErrorDTO errorUnauthorized(Exception e) {
        ErrorDTO dto = new ErrorDTO();
        dto.setMsg(e.getMessage());
        dto.setStatusCode(HttpStatus.UNAUTHORIZED.value());
        return dto;
    }
    @ExceptionHandler(value = {InvalidArticle.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorDTO errorInvalidArticle(Exception e) {
        ErrorDTO dto = new ErrorDTO();
        dto.setMsg(e.getMessage());
        dto.setStatusCode(HttpStatus.BAD_REQUEST.value());
        return dto;
    }
}
