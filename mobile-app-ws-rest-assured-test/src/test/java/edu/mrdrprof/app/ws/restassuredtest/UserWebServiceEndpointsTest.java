package edu.mrdrprof.app.ws.restassuredtest;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Alex Golub
 * @since 02-Apr-21 5:39 PM
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserWebServiceEndpointsTest {
  public static final int USER_ID_LENGTH = 30;
  private final String CONTEXT_PATH = "/restful-ws";
  private final String JSON = "application/json";
  private static String userId;
  private static String authorizationToken;
  private final String email = "alex@email.com";

  @BeforeEach
  void setUp() {
    RestAssured.baseURI = "http://localhost";
    RestAssured.port = 8080;
  }

  @Test
  @Order(1)
  void userLogin() {
    // http://localhost:8080/restful-ws/users/login
    Response response = given()
            .contentType(JSON)
            .accept(JSON)
            .body(getLoginDetails())
            .when()
            .post(CONTEXT_PATH + "/users/login")
            .then()
            .statusCode(HttpStatus.SC_OK)
            .extract()
            .response();

    authorizationToken = response.header("Authorization");
    assertNotNull(authorizationToken);
    assertTrue(authorizationToken.startsWith("Bearer "));

    userId = response.header("UserID");
    assertNotNull(userId);
    assertEquals(USER_ID_LENGTH, userId.length());
  }

  final Map<String, String> getLoginDetails() {
    return Map.of("email", "alex@email.com",
                  "password", "1234");
  }

  @Test
  @Order(2)
  void getUser() {
    // http://localhost:8080/restful-ws/users/{userId}
    Response response = given()
            .pathParam("userId", userId)
            .header("Authorization", UserWebServiceEndpointsTest.authorizationToken)
            .accept(JSON)
            .when()
            .get(CONTEXT_PATH + "/users/{userId}")
            .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(JSON)
            .extract()
            .response();

    String firstName = response.jsonPath().getString("firstName");
    String lastName = response.jsonPath().getString("lastName");
    String userId = response.jsonPath().getString("userId");
    String email = response.jsonPath().getString("email");
    List<Map<String, String>> addresses = response.jsonPath().getList("addresses");

    assertNotNull(firstName);
    assertNotNull(lastName);
    assertNotNull(userId);
    assertNotNull(email);
    assertNotNull(addresses);

    assertEquals(email, this.email);
    assertEquals(userId, UserWebServiceEndpointsTest.userId);

    int addressIdLength = addresses.get(0).get("addressId").length();
    int addressesSize = addresses.size();
    assertEquals(2, addressesSize);
    assertEquals(USER_ID_LENGTH, addressIdLength);
  }
}
