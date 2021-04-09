package edu.mrdrprof.app.ws.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.mrdrprof.app.ws.SpringApplicationContext;
import edu.mrdrprof.app.ws.service.UserService;
import edu.mrdrprof.app.ws.ui.model.request.UserLoginRequestModel;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static edu.mrdrprof.app.ws.security.SecurityConstants.*;

/**
 * Filter to process user sign-in requests.
 * If authentication process successfully passed user
 * is granted with access to application.
 *
 * @author Mr.Dr.Professor
 * @since 3/21/2021 12:12 PM
 */
@AllArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
  private final AuthenticationManager authenticationManager;

  /**
   * First method to be triggered when user attempt to sign-in.
   * Sfw will try to load this user by email, if user with email not
   * found in DB attemptAuthentication method will throw exception otherwise,
   * user successfully authenticated and then token will be checked
   */
  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
                                              HttpServletResponse response) throws AuthenticationException {
    try {
      // read incoming JSON and parse it to UserLoginRequestModel
      UserLoginRequestModel credentials = new ObjectMapper()
              .readValue(request.getInputStream(), UserLoginRequestModel.class);

      // try authenticating this user with the sfw loaded user from UserServiceImpl
      return authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(
                      credentials.getEmail(),
                      credentials.getPassword(),
                      new ArrayList<>()
              ));
    } catch (IOException ioException) {
      throw new RuntimeException("User authentication failed");
    }
  }

  /**
   * If user successfully passed the above attemptAuthentication method
   * generate an access token and append headers to http response with the
   * generated token.
   * Token is appended to http response and client responsibility to extract
   * the token key and store it for further http requests.
   * Each request to protected resources will not be accessible if token is not
   * present as part of the request (request will not be authorized).
   */
  @Override
  protected void successfulAuthentication(HttpServletRequest request,
                                          HttpServletResponse response,
                                          FilterChain chain,
                                          Authentication authResult) {
    String userName = ((UserPrincipal) authResult.getPrincipal()).getUsername();

    UserService userService = (UserService) SpringApplicationContext
            .getBean("userServiceImpl");

    response.addHeader(HEADER_STRING, TOKEN_PREFIX + generateToken(userName));
    response.addHeader("UserID", userService.getUserByEmail(userName).getUserId());
  }

  private String generateToken(String userName) {
    return Jwts.builder()
              .setSubject(userName)
              .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
              .signWith(SignatureAlgorithm.HS512, SecurityConstants.getTokenSecret())
              .compact();
  }
}

















