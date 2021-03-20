package edu.mrdrprof.app.ws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class MobileAppWsApplication {

  public static void main(String[] args) {
    SpringApplication.run(MobileAppWsApplication.class, args);
    System.out.println("Started..");
  }

  /** used for encoding user plain password before persisting to DB */
  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
