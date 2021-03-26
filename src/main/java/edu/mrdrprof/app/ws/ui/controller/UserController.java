package edu.mrdrprof.app.ws.ui.controller;

import edu.mrdrprof.app.ws.service.UserService;
import edu.mrdrprof.app.ws.shared.dto.UserDto;
import edu.mrdrprof.app.ws.ui.model.request.UserDetailsRequestModel;
import edu.mrdrprof.app.ws.ui.model.request.UserUpdateRequestModel;
import edu.mrdrprof.app.ws.ui.model.response.OperationRequestModel;
import edu.mrdrprof.app.ws.ui.model.response.RequestOperationName;
import edu.mrdrprof.app.ws.ui.model.response.RequestOperationStatus;
import edu.mrdrprof.app.ws.ui.model.response.UserRest;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.ArrayList;
import java.util.List;

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
  private final ModelMapper modelMapper;

  /**
   * using query string here to get the user request for how many
   * entries to display per page.
   * http://localhost:8080/users?page=1&limit=5
   */
  @GetMapping(produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
  public List<UserRest> getListOfUsers(@RequestParam(value = "page", defaultValue = "0") int page,
                                       @RequestParam(value = "limit", defaultValue = "5") int limit) {
    List<UserRest> list = new ArrayList<>();

    List<UserDto> userDtoList = userService.getUsers(page, limit);
    for (UserDto userDto : userDtoList) {
      UserRest user = new UserRest();
      BeanUtils.copyProperties(userDto, user);
      list.add(user);
    }

    return list;
  }

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
  public UserRest createUser(@Valid @RequestBody UserDetailsRequestModel userDetails) {
    return modelMapper.map(userService.createUser(modelMapper.map(userDetails, UserDto.class)), UserRest.class);
  }

  @PutMapping(path = "/{userId}",
              consumes = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE},
              produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
  public UserRest updateUser(@PathVariable String userId,
                             @Valid @RequestBody UserUpdateRequestModel userDetails) {
    UserDto userDto = new UserDto();
    BeanUtils.copyProperties(userDetails, userDto);
    UserDto updatedUser = userService.updateUser(userId, userDto);

    UserRest userRest = new UserRest();
    BeanUtils.copyProperties(updatedUser, userRest);

    return userRest;
  }

  @DeleteMapping(path = "/{userId}")
  public OperationRequestModel deleteUser(@PathVariable String userId) {
    OperationRequestModel operationRequestModel = new OperationRequestModel();
    userService.deleteUser(userId);

    operationRequestModel.setOperationName(RequestOperationName.DELETE.toString());
    operationRequestModel.setOperationStatus(RequestOperationStatus.SUCCESS.toString());
    return operationRequestModel;
  }
}
