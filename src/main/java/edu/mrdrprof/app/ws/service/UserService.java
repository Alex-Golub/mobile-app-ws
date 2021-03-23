package edu.mrdrprof.app.ws.service;

import edu.mrdrprof.app.ws.shared.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author Mr.Dr.Professor
 * @since 20/03/2021 15:01
 */
public interface UserService extends UserDetailsService {
  UserDto createUser(UserDto userDto);

  UserDto getUserByEmail(String email);

  UserDto getUserByUserId(String userId);

  UserDto updateUser(String userId, UserDto userDto);
}
