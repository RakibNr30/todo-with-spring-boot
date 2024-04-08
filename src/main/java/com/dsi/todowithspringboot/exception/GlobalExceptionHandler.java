package com.dsi.todowithspringboot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ NoResourceFoundException.class })
    public String notFoundException(Model model, Exception e) {
        model.addAttribute("status", HttpStatus.NOT_FOUND);
        model.addAttribute("message", e.getMessage());
        return "front/error/index";
    }

    @ExceptionHandler({ Exception.class })
    public String exception(Model model, Exception e) {
        model.addAttribute("status", HttpStatus.BAD_REQUEST);
        model.addAttribute("message", e.getMessage());
        return "front/error/index";
    }
}
