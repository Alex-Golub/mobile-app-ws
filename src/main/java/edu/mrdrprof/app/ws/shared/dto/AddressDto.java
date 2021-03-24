package edu.mrdrprof.app.ws.shared.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Mr.Dr.Professor
 * @since 24-Mar-21 3:44 PM
 */
@NoArgsConstructor
@Getter
@Setter
public class AddressDto implements Serializable {
  private static final long serialVersionUID = 7743451213588904562L;

  private long id;
  private String city;
  private String country;
  private String streetName;
  private String postalCode;
  private String type;
  private UserDto userDto;
}
