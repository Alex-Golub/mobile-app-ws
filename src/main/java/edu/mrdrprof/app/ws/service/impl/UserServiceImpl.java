package edu.mrdrprof.app.ws.service.impl;

import edu.mrdrprof.app.ws.ApplicationProperties;
import edu.mrdrprof.app.ws.exceptions.UserServiceException;
import edu.mrdrprof.app.ws.io.entity.UserEntity;
import edu.mrdrprof.app.ws.repository.UserRepository;
import edu.mrdrprof.app.ws.service.UserService;
import edu.mrdrprof.app.ws.shared.Utils;
import edu.mrdrprof.app.ws.shared.dto.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @author Mr.Dr.Professor
 * @since 20/03/2021 15:02
 */
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
  private static final int PUBLIC_USER_ID_LENGTH = 30;
  private final UserRepository userRepository;
  private final Utils utils;
  private final BCryptPasswordEncoder passwordEncoder;
  private final ApplicationProperties applicationProperties;

  @Override
  public UserDto createUser(UserDto userDto) {
    UserEntity userEntity = userRepository.findUserEntityByEmail(userDto.getEmail());
    if (userEntity != null) {
      throw new UserServiceException(applicationProperties.getProperty("recordAlreadyExists"));
    }

    userEntity = new UserEntity();
    BeanUtils.copyProperties(userDto, userEntity);

    userEntity.setUserId(utils.generateUserId(PUBLIC_USER_ID_LENGTH));
    userEntity.setEncryptedPassword(passwordEncoder.encode(userDto.getPassword()));

    UserEntity storedUserDetails = userRepository.save(userEntity);
    UserDto returnValue = new UserDto();
    BeanUtils.copyProperties(storedUserDetails, returnValue);

    return returnValue;
  }

  @Override
  public UserDto getUserByEmail(String email) {
    UserEntity UserEntity = userRepository.findUserEntityByEmail(email);
    if (UserEntity == null) {
      throw new UsernameNotFoundException("There is no user with email " + email);
    }

    UserDto userDto = new UserDto();
    BeanUtils.copyProperties(UserEntity, userDto);
    return userDto;
  }

  @Override
  public UserDto getUserByUserId(String userId) {
    UserEntity userEntity = userRepository.findUserEntityByUserId(userId);
    if (userEntity == null) {
      throw new UserServiceException(applicationProperties.getProperty("noRecordFound"));
    }

    UserDto userDto = new UserDto();
    BeanUtils.copyProperties(userEntity, userDto);
    return userDto;
  }

  @Override
  public UserDto updateUser(String userId, UserDto userDto) {
    UserEntity userEntity = userRepository.findUserEntityByUserId(userId);
    if (userEntity == null) {
      throw new UserServiceException(applicationProperties.getProperty("couldNotUpdateRecord"));
    }

    userEntity.setFirstName(userDto.getFirstName());
    userEntity.setLastName(userDto.getLastName());

    UserEntity save = userRepository.save(userEntity);
    BeanUtils.copyProperties(save, userDto);

    return userDto;
  }

  @Override
  public void deleteUser(String userId) {
    UserEntity userEntity = userRepository.findUserEntityByUserId(userId);
    if (userEntity == null) {
      throw new UserServiceException(applicationProperties.getProperty("couldNotUpdateDelete"));
    }

    userRepository.delete(userEntity);
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

    return new User(userEntity.getEmail(),
                    userEntity.getEncryptedPassword(),
                    new ArrayList<>());
  }
}
