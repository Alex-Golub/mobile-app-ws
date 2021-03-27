package edu.mrdrprof.app.ws.ui.model.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Users can only update their firstName and lastName for now
 *
 * @author Mr.Dr.Professor
 * @since 27-Mar-21 4:37 PM
 */
@NoArgsConstructor
@Getter
@Setter
public class UserUpdateRest {
  private String userId;
  private String firstName;
  private String lastName;
}
