package edu.mrdrprof.app.ws;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class to enable CORS globally
 * or control which controller classes for each
 * origin can accept and respond to different domains
 *
 * @author Alex Golub
 * @since 04-Apr-21 7:29 PM
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/users/**")         // allow all method is UserController
            .allowedMethods("*")     // allow this https request
            .allowedOrigins("http://localhost:8080"); // only from this origin
  }
}
