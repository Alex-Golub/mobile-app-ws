package edu.mrdrprof.app.ws.ui.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Mr.Dr.Professor
 * @since 3/23/2021 12:46 PM
 */
@NoArgsConstructor
@Getter
@Setter
public class UserUpdateRequestModel {
  @NotNull(message = "First name can't be empty or missing")
  @Size(min = 2, message = "First name must be at least {min} characters long")
  private String firstName;

  @NotNull(message = "Last name can't be empty or missing")
  @Size(min = 1, message = "Last name must be at least {min} character long")
  private String lastName;
}
