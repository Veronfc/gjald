package com.github.veronfc.gjald;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import jakarta.persistence.EntityNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(NoResourceFoundException.class)
  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  public ModelAndView handleNoResourceFoundException(Exception ex) {
    ModelAndView model = new ModelAndView("error");
    model.addObject("exception", "This page does not exist");
    return model;
  }

  @ExceptionHandler(EntityNotFoundException.class)
  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  public ModelAndView handleEntityNotFoundException(Exception ex) {
    ModelAndView model = new ModelAndView("error");
    model.addObject("exception", ex.getMessage());
    return model;
  }
}
