package edu.mrdrprof.app.ws.repository;

import edu.mrdrprof.app.ws.io.entity.AddressEntity;
import edu.mrdrprof.app.ws.io.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration test, Spring ApplicationContext is used
 * to autowire userRepository.
 * Can be converted to Unit test using Mockito @MockBean UserRepository
 *
 * @author Alex Golub
 * @since 03-Apr-21 1:19 PM
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserRepositoryTest {

  @Autowired
  UserRepository userRepository;

  private static boolean recordsCreated;

  @BeforeEach
  void setUp() {
    if (!recordsCreated) { // run createRecords method once for entire tests
      createRecords();
      recordsCreated = true;
    }
  }

  private void createRecords() {
    AddressEntity addressEntity1 = AddressEntity.builder()
            .addressId("grtlkrkjop45")
            .city("Tel-Aviv")
            .country("Israel")
            .streetName("kjndslfjhklsdf")
            .postalCode("fgngj333")
            .type("billing")
            .build();

    UserEntity entity1 = UserEntity.builder()
            .userId("kljk3jh4l2")
            .firstName("alex") // lowercase
            .lastName("Go")
            .encryptedPassword("qwer")
            .email("test@test.com")
            .emailVerificationStatus(true) // status change
            .addresses(List.of(addressEntity1))
            .build();

    userRepository.save(entity1);

    AddressEntity addressEntity2 = AddressEntity.builder()
            .addressId("egreg345gber5tb")
            .city("Tel-Aviv")
            .country("Israel")
            .streetName("kjndslfjhklsdf")
            .postalCode("fgngj333")
            .type("billing")
            .build();

    UserEntity entity2 = UserEntity.builder()
            .userId("1234abcd")
            .firstName("ALEX") // uppercase
            .lastName("Go")
            .encryptedPassword("qwer")
            .email("test2@test2.com")
            .emailVerificationStatus(true) // status change
            .addresses(List.of(addressEntity2))
            .build();

    userRepository.save(entity2);
  }

  @Test
  void findAllUsersWithUnConfirmationEmailAddress() {
    PageRequest pageRequest = PageRequest.of(0, 2); // two records per-page
    Page<UserEntity> userEntityPage =
            userRepository.findAllUsersWithConfirmationEmailAddress(pageRequest);
    assertNotNull(userEntityPage);

    List<UserEntity> content = userEntityPage.getContent();
    assertEquals(2, content.size());
  }

  @Test
  void findByFirstName() {
    List<UserEntity> entityList = userRepository.findUserByFirstName("alex");
    assertNotNull(entityList);
    assertEquals(2, entityList.size());
  }

  @Test
  void findUserByLastName() {
    List<UserEntity> entityList = userRepository.findUserByLastName("Go");
    assertNotNull(entityList);
    assertEquals(2, entityList.size());
    assertEquals("alex", entityList.get(0).getFirstName());
  }

  @Test
  void findUserByFirstNameEmailAndLastName() {
    List<UserEntity> entityList = userRepository.findUserByFirstNameEmailAndLastName(
            "test2@test2.com", "Go", "ALEX");
    assertNotNull(entityList);
    assertEquals(1, entityList.size());
  }

  @Test
  void findUserByKeyword() {
    String keyword = "ex";
    List<UserEntity> entityList = userRepository.findUserByKeyword(keyword);
    assertNotNull(entityList);
    assertEquals(1, entityList.size()); // finds only alex not ALEX
  }

  @Test
  void updateUserEmailVerificationStatus() {
    boolean status = false;
    String userId = "1234abcd";
    userRepository.updateUserEmailVerificationStatus(status, userId);

    UserEntity userEntity = userRepository.findUserEntityByUserId(userId);
    assertFalse(userEntity.isEmailVerificationStatus());

    status = true;
    userRepository.updateUserEmailVerificationStatus(status, userId);
    userEntity = userRepository.findUserEntityByUserId(userId);
    assertTrue(userEntity.isEmailVerificationStatus());
  }

  @Test
  void findUserEntityByFullName() {
    UserEntity entity = userRepository.findUserEntityByFullName("alex", "Go");
    assertNotNull(entity);
    assertEquals("alex", entity.getFirstName());
    assertEquals("Go", entity.getLastName());
  }

  @Test
  void findUserEntityFullNameByUserId() {
    String userId = "1234abcd";
    List<Object[]> list = userRepository.findUserEntityFullNameByUserId(userId);
    assertNotNull(list);
    assertEquals(1, list.size()); // { [<firstName>, <lastName>] }

    String firstName = String.valueOf(list.get(0)[0]);
    String lastName = String.valueOf(list.get(0)[1]);

    assertEquals("ALEX", firstName);
    assertEquals("Go", lastName);
  }

  @Test
  void updateUserEmailVerificationStatus2() {
    boolean status = false;
    String userId = "1234abcd";
    userRepository.updateUserEmailVerificationStatus2(status, userId);

    UserEntity userEntity = userRepository.findUserEntityByUserId(userId);
    assertFalse(userEntity.isEmailVerificationStatus());

    status = true;
    userRepository.updateUserEmailVerificationStatus2(status, userId);
    userEntity = userRepository.findUserEntityByUserId(userId);
    assertTrue(userEntity.isEmailVerificationStatus());
  }
}