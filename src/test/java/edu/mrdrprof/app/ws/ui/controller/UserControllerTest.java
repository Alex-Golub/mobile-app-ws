package edu.mrdrprof.app.ws.ui.controller;

import edu.mrdrprof.app.ws.service.UserService;
import edu.mrdrprof.app.ws.shared.dto.AddressDto;
import edu.mrdrprof.app.ws.shared.dto.UserDto;
import edu.mrdrprof.app.ws.ui.model.response.UserRest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

class UserControllerTest {
  @Mock
  UserService userService;
  @InjectMocks
  UserController userController;

  UserDto userDto;
  final String USER_ID = "1gdsfgkjnlkntg1ku3rt3";

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this); // init mocks

    userDto = UserDto.builder()
            .firstName("Alex")
            .lastName("Go")
            .email("text@test.com")
            .userId(USER_ID)
            .addresses(getAddressDtoList())
            .build();
  }

  @Test
  void testGetUser() {
    when(userService.getUserByUserId(anyString())).thenReturn(userDto);

    UserRest userRest = userController.getUser(USER_ID);
    assertNotNull(userRest);
    assertEquals(userDto.getFirstName(), userRest.getFirstName());
    assertEquals(userDto.getLastName(), userRest.getLastName());
    assertEquals(userDto.getEmail(), userRest.getEmail());
    assertEquals(userDto.getUserId(), userRest.getUserId());
    assertEquals(userDto.getAddresses().size(), userRest.getAddresses().size());
  }

  private List<AddressDto> getAddressDtoList() {
    AddressDto addressDto1 = new AddressDto();
    addressDto1.setAddressId("51knh5lkj1h124313");
    addressDto1.setCity("Israel");
    addressDto1.setStreetName("5th Shit hole Ave.");
    addressDto1.setType("Shipping");

    AddressDto addressDto2 = new AddressDto();
    addressDto2.setAddressId("531k5;3lm52jtiprjtg34");
    addressDto2.setCity("USA");
    addressDto2.setStreetName("1st Hole in the Ground Main");
    addressDto2.setType("Billing");

    List<AddressDto> addressDtoList = new ArrayList<>();
    addressDtoList.add(addressDto1);
    addressDtoList.add(addressDto2);

    return addressDtoList;
  }
}