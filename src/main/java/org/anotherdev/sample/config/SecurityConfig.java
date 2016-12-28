package org.anotherdev.sample.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author Anouar
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  @Qualifier("userDetailsService")
  private UserDetailsService userDetailsService;

  // @formatter:off
  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {
    // static resources
    httpSecurity.authorizeRequests()
            .antMatchers("/css/**", "/images/**").permitAll();
    // authorizeRequests
    httpSecurity.authorizeRequests()
            .antMatchers("/signin").anonymous()
            .antMatchers("/hash/bcrypt/*").hasRole("ADMIN")
            .anyRequest().authenticated()
            .and()
          .formLogin()
          //.usernameParameter("j_username") // default is username
          //.passwordParameter("j_password") // default is password
            .loginPage("/signin")
            .failureUrl("/signin-error")
            .defaultSuccessUrl("/specifications", true)
            .and()
          .logout()
            .logoutUrl("/signout")
            .logoutSuccessUrl("/signin");
  }
  // @formatter:on
  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth.inMemoryAuthentication();
    auth.userDetailsService(userDetailsService)
      .passwordEncoder(passwordEncoder());
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
