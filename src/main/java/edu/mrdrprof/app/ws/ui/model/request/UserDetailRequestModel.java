package edu.mrdrprof.app.ws.ui.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
  private String firstName;
  private String lastName;
  private String email;
  private String password;
}
