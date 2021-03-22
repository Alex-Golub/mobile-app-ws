package edu.mrdrprof.app.ws.exceptions;

/**
 * @author Mr.Dr.Professor
 * @since 3/22/2021 4:24 PM
 */
public class UserServiceException extends RuntimeException {
  private static final long serialVersionUID = 3689362745218651843L;

  public UserServiceException(String message) {
    super(message);
  }
}
