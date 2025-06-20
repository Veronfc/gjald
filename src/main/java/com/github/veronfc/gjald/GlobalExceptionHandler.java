package com.github.veronfc.gjald;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ServerErrorException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import jakarta.persistence.EntityNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(NoResourceFoundException.class)
  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  public ModelAndView handleNoResourceFoundException(Exception ex) {
    return new ModelAndView("error", "exception", "This page does not exist");
  }

  @ExceptionHandler(EntityNotFoundException.class)
  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  public ModelAndView handleEntityNotFoundException(Exception ex) {
    return new ModelAndView("error", "exception", ex.getMessage());
  }

  @ExceptionHandler(ServerErrorException.class)
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  public ModelAndView handleServerErrorException(Exception ex) {
    return new ModelAndView("error", "exception", ex.getMessage());
  }
}
