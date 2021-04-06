package edu.mrdrprof.app.ws.ui.controller;

import edu.mrdrprof.app.ws.ui.model.request.LoginRequestModel;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;

/**
 * @author Alex Golub
 * @since 06-Apr-21 12:24 PM
 */
@RestController
public class LoginController {

  @ApiResponses(value = {
          @ApiResponse(
                  code = 200,
                  message = "Response Headers",
                  responseHeaders = {
                          @ResponseHeader(name = "Authorization",
                                          description = "Bearer <JWT value here>",
                                          response = String.class),
                          @ResponseHeader(name = "userId",
                                          description = "<Public User id value here>",
                                          response = String.class)
                  })
  })
  @PostMapping(path = "/users/login",
               consumes = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
  public void userLogin(@RequestBody LoginRequestModel requestModel) {
    throw new IllegalArgumentException("This method will be processed by Spring Security");
  }
}
