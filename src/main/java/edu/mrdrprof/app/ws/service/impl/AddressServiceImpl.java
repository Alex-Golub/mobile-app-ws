package edu.mrdrprof.app.ws.service.impl;

import edu.mrdrprof.app.ws.ApplicationProperties;
import edu.mrdrprof.app.ws.exceptions.AddressServiceException;
import edu.mrdrprof.app.ws.io.entity.AddressEntity;
import edu.mrdrprof.app.ws.io.entity.UserEntity;
import edu.mrdrprof.app.ws.repository.AddressRepository;
import edu.mrdrprof.app.ws.repository.UserRepository;
import edu.mrdrprof.app.ws.service.AddressService;
import edu.mrdrprof.app.ws.shared.dto.AddressDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Mr.Dr.Professor
 * @since 27-Mar-21 2:29 PM
 */
@Service
@AllArgsConstructor
public class AddressServiceImpl implements AddressService {
  private final AddressRepository addressRepository;
  private final UserRepository userRepository;
  private final ApplicationProperties properties;

  @Override
  public List<AddressDto> getAddresses(String userId) {
    UserEntity userEntity = userRepository.findUserEntityByUserId(userId);
    valid(userEntity);

    // alternative method is to query address repository for userEntity addresses list
//    List<AddressEntity> userAddresses = addressRepository.findAllByUserDetails(userEntity);

    // addresses can never be null because when new user created
    // at least one address must be provided (see UserDetailsRequestModel addresses field @Size)
    return new ModelMapper().map(userEntity.getAddresses(),
                                 new TypeToken<List<AddressDto>>() {}.getType());
  }

  @Override
  public AddressDto getUserAddress(String addressId) {
    AddressEntity addressEntity = addressRepository.findByAddressId(addressId);
    valid(addressEntity);

    return new ModelMapper().map(addressEntity, AddressDto.class);
  }

  private void valid(Object o) {
    if (o == null) {
      throw new AddressServiceException(properties.getProperty("noRecordFound"));
    }
  }
}
