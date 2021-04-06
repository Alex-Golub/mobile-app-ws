package edu.mrdrprof.app.ws;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Configure Swagger where to look for
 * endpoints in this project
 *
 * @author Alex Golub
 * @since 04-Apr-21 8:47 PM
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

  @Bean
  public Docket apiDocket() {
    return new Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.basePackage("edu.mrdrprof.app.ws"))
            .paths(PathSelectors.any()) // consider all methods of this application
            .build();
  }
}
