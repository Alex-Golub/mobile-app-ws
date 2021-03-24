package edu.mrdrprof.app.ws.ui.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @author Mr.Dr.Professor
 * @since 24-Mar-21 3:32 PM
 */
@NoArgsConstructor
@Getter
@Setter
public class AddressRequestModel {
  @NotNull(message = "City field can't be empty or missing")
  private String city;

  @NotNull(message = "Country field can't be empty or missing")
  private String country;

  @NotNull(message = "Street name field can't be empty or missing")
  private String streetName;

  @NotNull(message = "Postal code field can't be empty or missing")
  private String postalCode;

  @NotNull(message = "Type field can't be empty or missing")
  private String type;
}
