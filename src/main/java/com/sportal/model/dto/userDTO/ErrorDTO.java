package com.sportal.model.dto.userDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ErrorDTO extends RuntimeException{
    private String msg;
    private int statusCode;
}