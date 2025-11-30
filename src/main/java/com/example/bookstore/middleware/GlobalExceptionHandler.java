package com.example.bookstore.middleware;

import com.example.bookstore.exceptions.RentalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ModelAndView handleGenericException(Exception ex) {
        log.error("An unexpected error occurred: {}", ex.getMessage(), ex);
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        mav.addObject("error", "Something went wrong");
        mav.addObject("message", "An unexpected error occurred. Please try again later.");
        mav.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        return mav;
    }

    @ExceptionHandler(RentalException.class)
    public ModelAndView handleRentalException(Exception ex) {
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("status", HttpStatus.BAD_REQUEST.value());
        mav.addObject("error", "Something went wrong");
        mav.addObject("message", ex.getMessage());
        mav.setStatus(HttpStatus.BAD_REQUEST);
        return mav;
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ModelAndView handleNoResourceFoundException() {
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("status", HttpStatus.NOT_FOUND.value());
        mav.addObject("error", "Something went wrong");
        mav.addObject("message", "The requested resource was not found.");
        mav.setStatus(HttpStatus.NOT_FOUND);
        return mav;
    }
}