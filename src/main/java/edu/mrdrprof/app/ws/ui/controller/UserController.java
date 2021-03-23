package edu.mrdrprof.app.ws.ui.controller;

import edu.mrdrprof.app.ws.service.UserService;
import edu.mrdrprof.app.ws.shared.dto.UserDto;
import edu.mrdrprof.app.ws.ui.model.request.UserDetailsRequestModel;
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
  public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) {
    UserDto userDto = new UserDto();
    BeanUtils.copyProperties(userDetails, userDto);
    UserDto storedUser = userService.createUser(userDto);
    UserRest userRest = new UserRest();
    BeanUtils.copyProperties(storedUser, userRest);

    return userRest;
  }

  @PutMapping(path = "/{userId}",
              consumes = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE},
              produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
  public UserRest updateUser(@PathVariable String userId, @RequestBody UserDetailsRequestModel userDetails) {
    UserDto userDto = new UserDto();
    BeanUtils.copyProperties(userDetails, userDto);
    UserDto updatedUser = userService.updateUser(userId, userDto);

    UserRest userRest = new UserRest();
    BeanUtils.copyProperties(updatedUser, userRest);

    return userRest;
  }

  @DeleteMapping
  public String deleteUser() {
    return "deleteUser()";
  }

}
