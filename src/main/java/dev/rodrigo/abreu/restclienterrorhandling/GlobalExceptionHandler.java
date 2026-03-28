package dev.rodrigo.abreu.restclienterrorhandling;

import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpStatusCodeException;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(HttpStatusCodeException.class)
  public ProblemDetail handle(HttpStatusCodeException ex){
    return ProblemDetail.forStatusAndDetail(ex.getStatusCode(), ex.getMessage());
  }
}
