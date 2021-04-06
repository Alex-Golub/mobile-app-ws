package edu.mrdrprof.app.ws;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.Set;

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
  Contact contact = new Contact(
          "Alex Go",
          "https://www.youtube.com/watch?v=SkgTxQm9DWM",
          "email@email.com"
  );

  ApiInfo apiInfo = new ApiInfo(
          "Mobile-App RESTful Web-Service Documentation",
          "This page document Mobile-App RESTful Web-Service end points",
          "1.0",
          "#",
          contact,
          "Apache 2.0",
          "http://www.apache.org/licenses/LICENSE-2.0",
          Collections.emptyList()
  );

  @Bean
  public Docket apiDocket() {
    return new Docket(DocumentationType.SWAGGER_2)
            .protocols(Set.of("HTTP", "HTTPS")) // supported request protocols
            .apiInfo(apiInfo) // customize swagger info page
            .select()
            .apis(RequestHandlerSelectors.basePackage("edu.mrdrprof.app.ws"))
            .paths(PathSelectors.any()) // consider all methods of this application
            .build();
  }
}
