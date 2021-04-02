package edu.mrdrprof.app.ws.restassuredtest;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Alex Golub
 * @since 02-Apr-21 3:04 PM
 */
class TestCreateUser {
  private final String CONTEXT_PATH = "/restful-ws";
  private final String JSON = "application/json";

  @BeforeEach
  void setUp() {
    RestAssured.baseURI = "http://localhost";
    RestAssured.port = 8080;
  }

  @Test
  final void testCreateUser() {
    Map<String, Object> userDetails = getUserDetails();

    Response response = given()
            .contentType(JSON)
            .accept(JSON)
            .body(userDetails)
            .when()
            .post(CONTEXT_PATH + "/users")
            .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(JSON)
            .extract()
            .response();

    String userId = response.jsonPath().getString("userId");
    assertNotNull(userId);

    try {
      // covert json response to string
      JSONObject jsonObject = new JSONObject(response.body().asString());

      // retrieve addresses key value from jsonObject as Array
      JSONArray addresses = jsonObject.getJSONArray("addresses");
      assertNotNull(addresses);

      // validate that billing and shipping retuned as response
      assertEquals(2, addresses.length());

      // validate addressId length was generated with length of 30
      int addressId = addresses.getJSONObject(0).getString("addressId").length();
      assertEquals(30, addressId);

    } catch (JSONException e) {
      fail(e.getMessage());
    }
  }

  final Map<String, Object> getUserDetails() {
    List<Map<String, Object>> userAddresses = new ArrayList<>();

    Map<String, Object> shippingAddresses = new HashMap<>();
    shippingAddresses.put("city", "Tel-Aviv");
    shippingAddresses.put("country", "Israel");
    shippingAddresses.put("streetName", "123 Street name");
    shippingAddresses.put("postalCode", "abc123");
    shippingAddresses.put("type", "shipping");

    Map<String, Object> billingAddress = new HashMap<>();
    billingAddress.put("city", "Tel-Aviv");
    billingAddress.put("country", "Israel");
    billingAddress.put("streetName", "123 Street name");
    billingAddress.put("postalCode", "abc123");
    billingAddress.put("type", "billing");

    userAddresses.add(shippingAddresses);
    userAddresses.add(billingAddress);

    Map<String, Object> userDetails = new HashMap<>();
    userDetails.put("firstName", "Alex");
    userDetails.put("lastName", "Go");
    userDetails.put("email", "xqq@email.com");
    userDetails.put("password", "1234");
    userDetails.put("addresses", userAddresses);

    return userDetails;
  }
}
