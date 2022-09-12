package com.example.studentmanagement.exception;

import org.springframework.boot.Banner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.NoSuchElementException;

@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(NoSuchElementException.class)
    public String handleNoSuchElement(NoSuchElementException ex, Model model){
        model.addAttribute("message", ex.getMessage());
        return "errorpage";
    }

    @ExceptionHandler(NotFoundException.class)
    public String handleResourceNotFound(NotFoundException ex, Model model){
        model.addAttribute("message", "Page not found");
        return "errorpage";
    }

    @ExceptionHandler(Exception.class)
    public String handleUnknownException(Exception ex, Model model){
        model.addAttribute("message", "Sorry, something wrong happened");
        return "errorpage";
    }
}
