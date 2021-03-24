package edu.mrdrprof.app.ws.ui.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Convert incoming user detail JSON payload into java object
 *
 * @author Mr.Dr.Professor
 * @since 20/03/2021 13:15
 */
@NoArgsConstructor
@Getter
@Setter
public class UserDetailsRequestModel {
  @NotNull(message = "First name can't be empty or missing")
  @Size(min = 2, message = "First name must be at least {min} characters long")
  private String firstName;

  @NotNull(message = "Last name can't be empty or missing")
  @Size(min = 1, message = "Last name must be at least {min} character long")
  private String lastName;

  @Email(message = "Invalid email address")
  private String email;

  @NotNull(message = "Password is required field")
  @Size(min = 4, message = "password must be at least {min} characters long")
  private String password;

  @NotNull(message = "Addressee is required field")
  @Size(min = 1, message = "At least one address should be provided")
  private List<AddressRequestModel> addresses;
}
