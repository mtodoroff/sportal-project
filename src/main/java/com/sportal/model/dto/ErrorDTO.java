package com.sportal.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ErrorDTO {
    private HttpStatus status;
    private String msg;
    private LocalDateTime dateTime;


}