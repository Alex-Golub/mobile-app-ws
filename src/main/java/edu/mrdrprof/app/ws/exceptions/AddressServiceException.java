package edu.mrdrprof.app.ws.exceptions;

/**
 * @author Mr.Dr.Professor
 * @since 27-Mar-21 2:43 PM
 */
public class AddressServiceException extends RuntimeException {
  private static final long serialVersionUID = 9177979263668133873L;

  public AddressServiceException(String message) {
    super(message);
  }
}
