package edu.mrdrprof.app.ws.ui.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Convert incoming user detail JSON payload into java object
 *
 * @author Mr.Dr.Professor
 * @since 20/03/2021 13:15
 */
@NoArgsConstructor
@Getter
@Setter
public class UserDetailRequestModel {

  @NotNull
  @NotEmpty(message = "field can't be empty")
  private String firstName;

  @NotNull
  @NotEmpty(message = "field can't be empty")
  private String lastName;

  @Email(message = "invalid email address")
  private String email;

  @Size(min = 4, message = "password must be at least {min} characters long")
  private String password;
}
