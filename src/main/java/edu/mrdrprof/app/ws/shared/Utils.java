package edu.mrdrprof.app.ws.shared;

import edu.mrdrprof.app.ws.security.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;

/**
 * @author Mr.Dr.Professor
 * @since 20/03/2021 16:32
 */
@Service
public class Utils {
  private static final String ALPHA_NUMERIC =
          "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
  private static final Random random = new SecureRandom();

  public String generateRandomString(int length) {
    StringBuilder sb = new StringBuilder(length);

    for (int i = 0; i < length; i++) {
      sb.append(ALPHA_NUMERIC.charAt(random.nextInt(ALPHA_NUMERIC.length())));
    }

    return sb.toString();
  }

  public static boolean hasTokenExpired(String token) {
    boolean expired;
    try {
      Claims claims = Jwts.parser()
              .setSigningKey(SecurityConstants.getTokenSecret())
              .parseClaimsJws(token)
              .getBody();

      expired = claims.getExpiration().before(new Date());
    } catch (ExpiredJwtException e) {
      expired = true;
    }

    return expired;
  }

  public String generatePasswordResetToken(String userId) {
    return Jwts.builder()
            .setSubject(userId)
            .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
            .signWith(SignatureAlgorithm.HS512, SecurityConstants.getTokenSecret())
            .compact();
  }
}
