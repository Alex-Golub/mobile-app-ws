package edu.mrdrprof.app.ws.service.impl;

import edu.mrdrprof.app.ws.io.entity.UserEntity;
import edu.mrdrprof.app.ws.repository.UserRepository;
import edu.mrdrprof.app.ws.service.UserService;
import edu.mrdrprof.app.ws.shared.Utils;
import edu.mrdrprof.app.ws.shared.dto.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Mr.Dr.Professor
 * @since 20/03/2021 15:02
 */
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
  private static final int PASSWORD_LENGTH = 30;
  private final UserRepository userRepository;
  private final Utils utils;
  private final BCryptPasswordEncoder passwordEncoder;

  @Override
  public UserDto createUser(UserDto userDto) {
    UserEntity userEntity = userRepository.findUserEntityByEmail(userDto.getEmail());
    if (userEntity != null) {
      throw new RuntimeException("User with such email already exists");
    }

    userEntity = new UserEntity();
    BeanUtils.copyProperties(userDto, userEntity);

    userEntity.setUserId(utils.generateUserId(PASSWORD_LENGTH));
    userEntity.setEncryptedPassword(passwordEncoder.encode(userDto.getPassword()));

    UserEntity storedUserDetails = userRepository.save(userEntity);
    UserDto returnValue = new UserDto();
    BeanUtils.copyProperties(storedUserDetails, returnValue);

    return returnValue;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    // TODO: tell spring by what parameter its should load a user
    return null;
  }
}
