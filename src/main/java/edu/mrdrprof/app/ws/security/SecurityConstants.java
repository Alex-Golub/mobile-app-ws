package edu.mrdrprof.app.ws.security;

/**
 * @author Mr.Dr.Professor
 * @since 20/03/2021 18:41
 */
public class SecurityConstants {
  public static final long EXPIRATION_TIME = 60 * 60 * 24 * 1000; // token is valid for 14 hours

  // adding headers to http response with token authorization
  public static final String HEADER_STRING = "Authorization";
  public static final String TOKEN_PREFIX = "Bearer ";

  public static final String SIGN_UP_URL = "/users";

  // will be used to encrypt access token
  public static final String TOKEN_SECRET = "k41231jfndl52kn43li5mgk2fgd34354";
}
