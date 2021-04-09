package edu.mrdrprof.app.ws.shared.dto;

import lombok.*;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * User data transfer object between different application layers
 *
 * @author Mr.Dr.Professor
 * @since 20/03/2021 14:18
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class UserDto implements Serializable {
  private static final long serialVersionUID = -6724621981044682155L;

  private long id; // actual user id in DB
  private String userId; // public userId safe to return
  private String firstName;
  private String lastName;
  private String email;
  private String password; // un-encrypted plain user password
  private String encryptedPassword;
  private String emailVerificationToken;
  private boolean emailVerificationStatus = false;
  private List<AddressDto> addresses;
  private Collection<String> roles;
}
