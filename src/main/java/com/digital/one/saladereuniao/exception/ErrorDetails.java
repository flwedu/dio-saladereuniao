package com.digital.one.saladereuniao.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class ErrorDetails {

    private LocalDate timestamp;
    private String message;
    private String details;
}
