package edu.mrdrprof.app.ws.service.impl;

import edu.mrdrprof.app.ws.ApplicationProperties;
import edu.mrdrprof.app.ws.exceptions.UserServiceException;
import edu.mrdrprof.app.ws.io.entity.RoleEntity;
import edu.mrdrprof.app.ws.io.entity.UserEntity;
import edu.mrdrprof.app.ws.repository.RoleRepository;
import edu.mrdrprof.app.ws.repository.UserRepository;
import edu.mrdrprof.app.ws.security.UserPrincipal;
import edu.mrdrprof.app.ws.service.UserService;
import edu.mrdrprof.app.ws.shared.Utils;
import edu.mrdrprof.app.ws.shared.dto.AddressDto;
import edu.mrdrprof.app.ws.shared.dto.UserDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Mr.Dr.Professor
 * @since 20/03/2021 15:02
 */
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
  private static final int ID_LENGTH = 30;
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final Utils utils;
  private final BCryptPasswordEncoder passwordEncoder;
  private final ApplicationProperties applicationProperties;

  @Override
  public UserDto createUser(UserDto userDto) {
    if (userRepository.findUserEntityByEmail(userDto.getEmail()) != null) {
      throw new UserServiceException(applicationProperties
                                             .getProperty("recordAlreadyExists"));
    }

    for (int i = 0; i < userDto.getAddresses().size(); i++) {
      AddressDto addressDto = userDto.getAddresses().get(i);
      addressDto.setUserDetails(userDto);
      addressDto.setAddressId(utils.generateRandomString(ID_LENGTH));
      userDto.getAddresses().set(i, addressDto);
    }

    userDto.setUserId(utils.generateRandomString(ID_LENGTH));
    userDto.setEncryptedPassword(passwordEncoder.encode(userDto.getPassword()));

    // Set user roles
    Collection<RoleEntity> roleEntityCollection = new HashSet<>();
    for (String role : userDto.getRoles()) {
      RoleEntity roleEntity = roleRepository.findByName(role);
      if (roleEntity != null) {
        roleEntityCollection.add(roleEntity);
      }
    }

    ModelMapper modelMapper = new ModelMapper();
    UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
    userEntity.setRoles(roleEntityCollection);

    return modelMapper.map(userRepository.save(userEntity),
                           UserDto.class);
  }

  @Override
  public UserDto getUserByEmail(String email) {
    UserEntity userEntity = userRepository.findUserEntityByEmail(email);
    if (userEntity == null) {
      throw new UsernameNotFoundException("There is no user with email " + email);
    }

    return new ModelMapper().map(userEntity, UserDto.class);
  }

  @Override
  public UserDto getUserByUserId(String userId) {
    UserEntity userEntity = userRepository.findUserEntityByUserId(userId);
    if (userEntity == null) {
      throw new UserServiceException(applicationProperties
                                             .getProperty("noRecordFound"));
    }

    return new ModelMapper().map(userEntity, UserDto.class);
  }

  @Override
  public UserDto updateUser(String userId, UserDto userDto) {
    UserEntity userEntity = userRepository.findUserEntityByUserId(userId);
    if (userEntity == null) {
      throw new UserServiceException(applicationProperties
                                             .getProperty("couldNotUpdateRecord"));
    }

    userEntity.setFirstName(userDto.getFirstName());
    userEntity.setLastName(userDto.getLastName());

    return new ModelMapper().map(userRepository.save(userEntity), UserDto.class);
  }

  @Override
  public void deleteUser(String userId) {
    UserEntity userEntity = userRepository.findUserEntityByUserId(userId);
    if (userEntity == null) {
      throw new UserServiceException(applicationProperties
                                             .getProperty("couldNotUpdateDelete"));
    }

    userRepository.delete(userEntity);
  }

  /**
   * Each request should return limited number of users entries rather
   * then all of the users entries, thus UserRepository interface will now
   * extend PagingAndSortingRepository interface which is an extended
   * interface of current CrudRepository
   */
  @Override
  public List<UserDto> getUsers(int page, int limit) {
    if (page > 0) {
      page -= 1; // offset page to start at index 1 rather the 0 based
    }

    return userRepository
            .findAll(PageRequest.of(page, limit))
            .stream()
            .map(userEntity -> new ModelMapper().map(userEntity, UserDto.class))
            .collect(Collectors.toList());
  }

  /**
   * Method will be triggered when client send POST
   * request with email and password.
   * sfw will check if there is a user with such email in the DB,
   * if user found then client is granted access token, otherwise
   * access if forbidden
   */
  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    UserEntity userEntity = userRepository.findUserEntityByEmail(email);
    if (userEntity == null) {
      throw new UsernameNotFoundException("There is no such user with email " + email);
    }

    return new UserPrincipal(userEntity);
  }
}
