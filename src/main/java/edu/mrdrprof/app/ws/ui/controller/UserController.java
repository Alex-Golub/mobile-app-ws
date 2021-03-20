package edu.mrdrprof.app.ws.ui.controller;

import edu.mrdrprof.app.ws.service.UserService;
import edu.mrdrprof.app.ws.shared.dto.UserDto;
import edu.mrdrprof.app.ws.ui.model.request.UserDetailRequestModel;
import edu.mrdrprof.app.ws.ui.model.response.UserRest;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @author Mr.Dr.Professor
 * @since 18/03/2021 21:08
 */
@RestController
@RequestMapping(path = "/users")
@AllArgsConstructor // constructor autowiring
public class UserController {
  private final UserService userService;

  @GetMapping(path = "/{userId}")
  public String getUser(@PathVariable String userId) {
    return "getUser(String userId)";
  }

  @PostMapping
  public UserRest createUser(@RequestBody UserDetailRequestModel userDetails) {
    UserDto userDto = new UserDto();
    BeanUtils.copyProperties(userDetails, userDto);
    UserDto storedUser = userService.createUser(userDto);
    UserRest userRest = new UserRest();
    BeanUtils.copyProperties(storedUser, userRest);

    return userRest;
  }

  @PutMapping
  public String updateUser() {
    return "updateUser()";
  }

  @DeleteMapping
  public String deleteUser() {
    return "deleteUser()";
  }

}
