package edu.mrdrprof.app.ws.service;

import edu.mrdrprof.app.ws.shared.dto.AddressDto;

import java.util.List;

/**
 * @author Mr.Dr.Professor
 * @since 27-Mar-21 2:28 PM
 */
public interface AddressService {
  List<AddressDto> getAddresses(String userId);
}
