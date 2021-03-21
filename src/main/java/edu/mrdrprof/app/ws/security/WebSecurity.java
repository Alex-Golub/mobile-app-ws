package edu.mrdrprof.app.ws.security;

import edu.mrdrprof.app.ws.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Application security configurations extending already
 * provided security methods from WebSecurityConfigurerAdapter class.
 * For customizing security for this application specific methods
 * will be override to meet requirements
 *
 * @author Mr.Dr.Professor
 * @since 20/03/2021 18:31
 */
@EnableWebSecurity
@AllArgsConstructor
public class WebSecurity extends WebSecurityConfigurerAdapter {
  private final UserService userService;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  /**
   * Configure which entry points will be permitted,
   * authentication filters
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable()
            .authorizeRequests()
            .mvcMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_URL)
            .permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .addFilter(authenticationFilter())
            .addFilter(new AuthorizationFilter(authenticationManager()));
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userService)
            .passwordEncoder(bCryptPasswordEncoder);
  }

  /** change spring default /login URL to /users/login */
  public AuthenticationFilter authenticationFilter() throws Exception {
    AuthenticationFilter filter = new AuthenticationFilter(authenticationManager());
    filter.setFilterProcessesUrl("/users/login");
    return filter;
  }
}
