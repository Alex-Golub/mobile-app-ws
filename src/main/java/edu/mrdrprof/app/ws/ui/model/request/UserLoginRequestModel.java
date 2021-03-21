package edu.mrdrprof.app.ws.ui.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

/**
 * @author Mr.Dr.Professor
 * @since 3/21/2021 11:17 AM
 */
@AllArgsConstructor
@Getter
public class UserLoginRequestModel {

  @Email(message = "Invalid email provided")
  private final String email;

  @Size(min = 4, message = "Password should be at least {min} characters long")
  private final String password;
}
