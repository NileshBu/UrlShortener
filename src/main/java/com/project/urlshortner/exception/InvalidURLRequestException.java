package com.project.urlshortner.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidURLRequestException extends RuntimeException {
 
  private static final long serialVersionUID = 5634869299137716028L;

  public InvalidURLRequestException(String exceptionMessage) {
    super(exceptionMessage);
    log.info(exceptionMessage);
  }
}