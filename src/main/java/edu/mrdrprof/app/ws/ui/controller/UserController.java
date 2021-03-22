package edu.mrdrprof.app.ws.ui.controller;

import edu.mrdrprof.app.ws.service.UserService;
import edu.mrdrprof.app.ws.shared.dto.UserDto;
import edu.mrdrprof.app.ws.ui.model.request.UserDetailRequestModel;
import edu.mrdrprof.app.ws.ui.model.response.UserRest;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;

/**
 * @author Mr.Dr.Professor
 * @since 18/03/2021 21:08
 */
@RestController
@RequestMapping(path = "/users")
@AllArgsConstructor // constructor autowiring
public class UserController {
  private final UserService userService;

  @GetMapping(path = "/{userId}",
              produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
  public UserRest getUser(@PathVariable String userId) {
    UserDto userDto = userService.getUserByUserId(userId);
    UserRest userRest = new UserRest();
    BeanUtils.copyProperties(userDto, userRest);
    return userRest;
  }

  @PostMapping(consumes = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE},
               produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
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
