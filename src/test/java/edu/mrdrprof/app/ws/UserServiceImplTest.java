package edu.mrdrprof.app.ws;

import edu.mrdrprof.app.ws.exceptions.UserServiceException;
import edu.mrdrprof.app.ws.io.entity.AddressEntity;
import edu.mrdrprof.app.ws.io.entity.UserEntity;
import edu.mrdrprof.app.ws.repository.UserRepository;
import edu.mrdrprof.app.ws.service.impl.UserServiceImpl;
import edu.mrdrprof.app.ws.shared.Utils;
import edu.mrdrprof.app.ws.shared.dto.AddressDto;
import edu.mrdrprof.app.ws.shared.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * @author Alex Golub
 * @since  31/03/2021 19:00
 */
class UserServiceImplTest {
  @Mock
  UserRepository userRepository;
  @Mock
  Utils utils;
  @Mock
  ApplicationProperties applicationProperties;
  @Mock
  BCryptPasswordEncoder passwordEncoder;
  @InjectMocks
  UserServiceImpl userService;

  UserEntity userEntity;
  String addressId = "13413klfn2l";
  String userId = "t1tkknkl3jhglngokl2432nlknkli";
  String encryptedPassword = "lju1381fno138o3ngfio2";

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    userEntity = UserEntity.builder()
            .id(1L)
            .firstName("Alex")
            .userId(userId)
            .encryptedPassword(encryptedPassword)
            .email("test@test.com")
            .addresses(getAddressEntityList())
            .build();
  }

  @Test
  void testGetUserByEmail() {
    when(userRepository.findUserEntityByEmail(anyString())).thenReturn(userEntity);

    UserDto userDto = userService.getUserByEmail(userEntity.getEmail());
    assertNotNull(userDto);
    assertEquals(userEntity.getFirstName(), userDto.getFirstName());
  }

  @Test
  final void testGetUserByEmail_UserNotFoundException() {
    when(userRepository.findUserEntityByEmail(anyString())).thenReturn(null);
    assertThrows(UsernameNotFoundException.class,
                 () -> userService.getUserByEmail(userEntity.getEmail()));
  }

  @Test
  final void testCreateUser_UserNotFoundException() {
    when(userRepository.findUserEntityByEmail(anyString())).thenReturn(userEntity);
    when(applicationProperties.getProperty("recordAlreadyExists")).thenReturn(anyString());

    UserDto userDto = new UserDto();
    userDto.setEmail(userEntity.getEmail());

    assertThrows(UserServiceException.class,
                 () -> userService.createUser(userDto));
  }

  @Test
  final void testCreateUser() {
    when(userRepository.findUserEntityByEmail(anyString())).thenReturn(null);
    when(utils.generateRandomString(anyInt())).thenReturn(addressId);
    when(utils.generateRandomString(anyInt())).thenReturn(userId);
    when(passwordEncoder.encode(anyString())).thenReturn(encryptedPassword);
    when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

    UserDto userDto = new UserDto();
    userDto.setAddresses(getAddressDtoList());

    UserDto createdUser = userService.createUser(userDto);

    assertNotNull(createdUser);
    assertEquals(userEntity.getUserId(), createdUser.getUserId());
    assertEquals(userEntity.getFirstName(), createdUser.getFirstName());
    assertEquals(userEntity.getEmail(), createdUser.getEmail());
    assertNotNull(createdUser.getUserId());

    assertEquals(userEntity.getAddresses().size(), createdUser.getAddresses().size());

    // check number of times a method has been executed
    verify(utils, times(3)).generateRandomString(anyInt());
    verify(userRepository, times(1)).save(any(UserEntity.class));
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

  private List<AddressEntity> getAddressEntityList() {
    List<AddressDto> addressDtoList = getAddressDtoList();
    Type listType = new TypeToken<List<AddressEntity>>() {}.getType();
    return new ModelMapper().map(getAddressDtoList(), listType);
  }
}