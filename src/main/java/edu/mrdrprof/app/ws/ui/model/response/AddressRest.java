package edu.mrdrprof.app.ws.ui.model.response;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Mr.Dr.Professor
 * @since 27-Mar-21 12:03 AM
 */
@Getter
@Setter
public class AddressRest {
  private String addressId;
  private String city;
  private String country;
  private String streetName;
  private String postalCode;
  private String type;
}
