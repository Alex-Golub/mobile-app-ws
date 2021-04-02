package edu.mrdrprof.app.ws.shared;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Integration test
 * We extends this class with SpringExtension.class
 * and mark it with SpringBootTest test will bring ApplicationContext
 * and we can get beans from it
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
class UtilsTest {
  public static final int ID_LENGTH = 30;

  @Autowired
  Utils utils;

  @BeforeEach
  void setUp() {
  }

  @Test
  void generateRandomString() {
    String userId1 = utils.generateRandomString(ID_LENGTH);
    String userId2 = utils.generateRandomString(ID_LENGTH);

    assertNotNull(userId1);
    assertNotNull(userId2);

    assertEquals(userId1.length(), ID_LENGTH);
    assertEquals(userId2.length(), ID_LENGTH);

    // two generated ID's should be unique values
    assertNotEquals(userId1, userId2);
  }

  @Test
  void hasTokenNotExpired() {
    String token = utils.generatePasswordResetToken("jkln123ihij");
    assertNotNull(token);
    assertFalse(Utils.hasTokenExpired(token));
  }

  @Test
  void hasTokenExpired() {
    String expiredToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMjNxcXdAZW1haWwuY29tIiwiZXhwIjoxNjE2OTI5NDg0fQ.dKGg-zJe6yxWnJezbbhWXnd-tthJE7JD3rh3IRRz3WlVepT4GbZCfOv86ulNOhGAWVl8IABlcSTQl9g5B_REPQ";
    assertTrue(Utils.hasTokenExpired(expiredToken));
  }
}