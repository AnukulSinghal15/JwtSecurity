package com.example.demo.exceptionHandling;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ExceptionResponse {

    Date date;
    String message;
    String details;
}
