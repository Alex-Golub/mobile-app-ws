package edu.mrdrprof.app.ws.security;

import edu.mrdrprof.app.ws.repository.UserRepository;
import edu.mrdrprof.app.ws.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * Application security configurations extending already
 * provided security methods from WebSecurityConfigurerAdapter class.
 * For customizing security for this application specific methods
 * will be override to meet requirements
 *
 * @author Mr.Dr.Professor
 * @since 20/03/2021 18:31
 */
@EnableGlobalMethodSecurity(securedEnabled = true) // selectively enable security annotations
@EnableWebSecurity
@AllArgsConstructor
public class WebSecurity extends WebSecurityConfigurerAdapter {
  private final UserService userService;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;
  private final UserRepository userRepository;

  /**
   * Configure which entry points will be permitted,
   * authentication filters, authorization filters,
   * convert from stateful to stateless (i.e. for each request
   * user must provide token header thus preventing caching the token
   * on the server side)
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.cors()
            .and()
            .csrf().disable()
            .authorizeRequests()
            .mvcMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_URL)
              .permitAll()
            .mvcMatchers(SecurityConstants.H2_CONSOLE)
              .permitAll()
            .mvcMatchers("/v2/api-docs", "configuration/**", "/swagger*/**", "/webjars/**")
              .permitAll()
//            .mvcMatchers(HttpMethod.DELETE, "/users/**").hasRole("ADMIN")
//            .mvcMatchers(HttpMethod.DELETE, "/users/**").hasAuthority("DELETE_AUTHORITY")
            .anyRequest()
            .authenticated()
            .and()
            .addFilter(authenticationFilter())
            .addFilter(new AuthorizationFilter(authenticationManager(), userRepository))
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    // allow h2 console load in browser window
    http.headers().frameOptions().disable();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userService)
            .passwordEncoder(bCryptPasswordEncoder);
  }

  /**
   * Change spring default /login URL to /users/login
   */
  public AuthenticationFilter authenticationFilter() throws Exception {
    AuthenticationFilter filter = new AuthenticationFilter(authenticationManager());
    filter.setFilterProcessesUrl("/users/login");
    return filter;
  }

  /**
   * Enabling cors() in configure method we must provid
   * corsConfigurationSource bean
   */
  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration corsConfiguration = new CorsConfiguration();

    corsConfiguration.setAllowedOrigins(List.of("http://localhost:8080"));
    corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
    corsConfiguration.setAllowCredentials(true);
    corsConfiguration.setAllowedHeaders(List.of("Authorization", "Content-Type"));

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", corsConfiguration);
    return source;
  }
}
