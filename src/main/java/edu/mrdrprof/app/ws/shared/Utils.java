package edu.mrdrprof.app.ws.shared;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Random;

/**
 * @author Mr.Dr.Professor
 * @since 20/03/2021 16:32
 */
@Component
public class Utils {
  private static final String ALPHA_NUMERIC = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
  private static final Random random = new SecureRandom();

  public String generateRandomString(int length) {
    StringBuilder sb = new StringBuilder(length);

    for (int i = 0; i < length; i++) {
      sb.append(ALPHA_NUMERIC.charAt(random.nextInt(ALPHA_NUMERIC.length())));
    }

    return sb.toString();
  }
}
