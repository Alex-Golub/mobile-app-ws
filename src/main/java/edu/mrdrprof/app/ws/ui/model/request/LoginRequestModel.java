package edu.mrdrprof.app.ws.ui.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Alex Golub
 * @since 06-Apr-21 12:26 PM
 */
@AllArgsConstructor
@Setter
@Getter
public class LoginRequestModel {
  private String email;
  private String password;
}
