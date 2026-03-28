package dev.rodrigo.abreu.restclienterrorhandling.exceptions;

import org.springframework.http.HttpStatusCode;

public class ApiException extends RuntimeException {

  private final HttpStatusCode statusCode;

  public ApiException(String message, HttpStatusCode statusCode) {
    super(message);
    this.statusCode = statusCode;
  }
}
