package edu.mrdrprof.app.ws;

import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * Read and return properties from
 * application properties file.
 *
 * @author Mr.Dr.Professor
 * @since 3/21/2021 4:42 PM
 */
@Component
@AllArgsConstructor
public class ApplicationProperties {
  private final Environment environment;

  public String getProperty(String property) {
    return environment.getProperty(property);
  }
}
