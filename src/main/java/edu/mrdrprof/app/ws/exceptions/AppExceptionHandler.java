package edu.mrdrprof.app.ws.exceptions;

import edu.mrdrprof.app.ws.ui.model.response.ErrorMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

/**
 * Handles application exception
 *
 * @author Mr.Dr.Professor
 * @since 3/22/2021 4:45 PM
 */
@ControllerAdvice
public class AppExceptionHandler {

  // specific UserServiceException handler
  @ExceptionHandler(value = {UserServiceException.class})
  public ResponseEntity<ErrorMessage> userServiceExceptionHandler(UserServiceException userServiceException) {
    return new ResponseEntity<>(getErrorMessage(userServiceException), INTERNAL_SERVER_ERROR);
  }

  // handel all other exceptions that may arise
  @ExceptionHandler(value = Exception.class)
  public ResponseEntity<Object> otherExceptionsHandler(Exception e) {
    return new ResponseEntity<>(getErrorMessage(e), INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(value = AddressServiceException.class)
  public ResponseEntity<ErrorMessage> addressServiceExceptionHandler(AddressServiceException e) {
    return new ResponseEntity<>(getErrorMessage(e), INTERNAL_SERVER_ERROR);
  }

  private ErrorMessage getErrorMessage(Exception e) {
    return new ErrorMessage(new Date(), e.getMessage());
  }
}
