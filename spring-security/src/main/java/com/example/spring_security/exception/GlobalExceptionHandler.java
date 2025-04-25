package com.example.spring_security.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ResponseBody
    @ExceptionHandler(AlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleUserAlreadyExistsException(AlreadyExistsException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(InvalidCredentialsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleInvalidCredentialsException(InvalidCredentialsException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(TokenExpiredException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleTokenExpiredException(TokenExpiredException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(InvalidRefreshTokenException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleInvalidRefreshTokenException(InvalidRefreshTokenException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleNotFoundException(NotFoundException ex) {
        return ex.getMessage();
    }
}
