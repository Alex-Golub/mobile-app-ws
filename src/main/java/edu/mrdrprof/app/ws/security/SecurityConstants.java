package edu.mrdrprof.app.ws.security;

import edu.mrdrprof.app.ws.ApplicationProperties;
import edu.mrdrprof.app.ws.SpringApplicationContext;

/**
 * @author Mr.Dr.Professor
 * @since 20/03/2021 18:41
 */
public class SecurityConstants {
  public static final long EXPIRATION_TIME = 60 * 60 * 24 * 1000; // token is valid for 1 day

  // adding headers to http response with token authorization
  public static final String HEADER_STRING = "Authorization";
  public static final String TOKEN_PREFIX = "Bearer ";
  public static final String SIGN_UP_URL = "/users";
  public static final String H2_CONSOLE = "/h2-console/**";

  public static String getTokenSecret() {
    return ((ApplicationProperties) SpringApplicationContext.getBean("applicationProperties"))
            .getProperty("tokenSecret");
  }
}
