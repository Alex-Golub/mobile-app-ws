package edu.mrdrprof.app.ws.ui.model.response;

import lombok.*;

/**
 * Convert java object into outgoing JSON payload response
 *
 * @author Mr.Dr.Professor
 * @since 20/03/2021 13:57
 */
@NoArgsConstructor
@Getter
@Setter
public class UserRest {
  private String userId; // not the actual id of user from DB
  private String firstName;
  private String lastName;
  private String email;
}
