package com.example.studentmanagement.exception;

import org.springframework.boot.Banner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.sql.SQLException;
import java.util.NoSuchElementException;

@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(NoSuchElementException.class)
    public String handleNoSuchElement(NoSuchElementException ex, Model model){
        model.addAttribute("message", "No such element");
        return "errorPage";
    }

    @ExceptionHandler(RuntimeException.class)
    public String handleResourceNotFound(RuntimeException ex, Model model){
        model.addAttribute("message", "Resource not found");
        return "errorPage";
    }

    @ExceptionHandler(Exception.class)
    public String handleUnknownException(Exception ex, Model model){
        model.addAttribute("message", "Sorry, something wrong happened");
        return "errorPage";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex, Model model){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.EXPECTATION_FAILED);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handleIllegalState(IllegalStateException ex, Model model){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SQLException.class)
    public String handleSQLException(SQLException ex, Model model){
        model.addAttribute("message", "SQL EXception");
        return "errorPage";
    }

    @ExceptionHandler(BadCredentialsException.class)
    public String handleBadCredential(BadCredentialsException ex, Model model){
        model.addAttribute("message", "Your account is currently disabled, contact admin for more information");
        return "errorPage";
    }
}
