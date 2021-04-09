package edu.mrdrprof.app.ws.security;

import edu.mrdrprof.app.ws.io.entity.UserEntity;
import edu.mrdrprof.app.ws.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Upon successful user sign-in authorization should be checked.
 *
 * @author Mr.Dr.Professor
 * @since 3/21/2021 2:58 PM
 */
public class AuthorizationFilter extends BasicAuthenticationFilter {
  private final UserRepository userRepository;

  public AuthorizationFilter(AuthenticationManager authenticationManager,
                             UserRepository userRepository) {
    super(authenticationManager);
    this.userRepository = userRepository;
  }

  /**
   * If user completed the sign-in process then the authorization of
   * that user should be checked.
   * User is authorized if token is appended to the request and it is valid
   * token. This method will check if the token is actually valid.
   */
  @Override
  protected void doFilterInternal(HttpServletRequest request,
                                  HttpServletResponse response,
                                  FilterChain chain) throws IOException, ServletException {
    String token = request.getHeader(SecurityConstants.HEADER_STRING);
    if (token == null || !token.startsWith(SecurityConstants.TOKEN_PREFIX)) {
      chain.doFilter(request, response); // continue to next filter in chain
      return;
    }

    UsernamePasswordAuthenticationToken authentication = getAuthentication(request);

    // place current user principal object to allow access from security
    // method/global annotations when using security expressions
    SecurityContextHolder.getContext().setAuthentication(authentication);
    chain.doFilter(request, response);
  }

  /**
   * Extract token from http request header authorization and if token is
   * valid grant this user authorization.
   */
  private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
    String header = request.getHeader(SecurityConstants.HEADER_STRING);

    if (header != null) {
      String token = header.replace(SecurityConstants.TOKEN_PREFIX, "");
      String email = Jwts.parser()
              .setSigningKey(SecurityConstants.getTokenSecret())
              .parseClaimsJws(token)
              .getBody()
              .getSubject();

      if (email != null) {
        UserEntity userEntity = userRepository.findUserEntityByEmail(email);
        if (userEntity == null) {
          return null;
        }

        UserPrincipal userPrincipal = new UserPrincipal(userEntity);
        return new UsernamePasswordAuthenticationToken(userPrincipal, // add userPrincipal object rather then only email
                                                       null,
                                                       userPrincipal.getAuthorities());
      }
    }

    return null;
  }
}
