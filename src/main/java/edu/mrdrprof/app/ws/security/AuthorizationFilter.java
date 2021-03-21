package edu.mrdrprof.app.ws.security;

import io.jsonwebtoken.Jwts;
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
  public AuthorizationFilter(AuthenticationManager authenticationManager) {
    super(authenticationManager);
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

    // place authentication token for this user into SecurityContextHolder
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
      String principal = Jwts.parser()
              .setSigningKey(SecurityConstants.TOKEN_SECRET)
              .parseClaimsJws(token)
              .getBody()
              .getSubject();

      if (principal != null) {
        return new UsernamePasswordAuthenticationToken(principal, null, new ArrayList<>());
      }
    }

    return null;
  }
}
