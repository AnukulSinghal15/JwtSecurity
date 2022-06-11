package com.example.demo.exceptionHandling;

import com.example.demo.exceptionHandling.ExceptionResponse;
import com.example.demo.exceptionHandling.StudentNotFoundException;
import com.example.demo.exceptionHandling.StudentParametersException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    Logger logger= LoggerFactory.getLogger(ExceptionHandler.class);

    @ExceptionHandler(StudentParametersException.class)
    public ResponseEntity<Object> handleStudentParameterException(
            StudentParametersException exception, WebRequest request){

        ExceptionResponse exceptionResponse= new ExceptionResponse(
                new Date(), exception.getMessage(), request.getDescription(false)
        );

        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<Object> handleStudentNotFoundException(
            StudentNotFoundException exception, WebRequest request){

        ExceptionResponse exceptionResponse= new ExceptionResponse(
                new Date(), exception.getMessage(), " "
        );

        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoUsersExistException.class)
    public ResponseEntity<Object> handleNousersExist(
            StudentNotFoundException exception, WebRequest request){

        ExceptionResponse exceptionResponse= new ExceptionResponse(
                new Date(), exception.getMessage(), " "
        );

        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }
}
