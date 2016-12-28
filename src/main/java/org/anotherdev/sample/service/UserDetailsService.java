package org.anotherdev.sample.service;

import java.util.ArrayList;
import java.util.List;

import org.anotherdev.sample.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * @author Anouar
 */
@Component
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username);
    if (null == user) {
      throw new UsernameNotFoundException("User " + username + " not found");
    }
    List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
    for (String userRole : user.getUserRoles()) {
      authorities.add(new SimpleGrantedAuthority(userRole));
    }
    return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), true, true, true, true, authorities);
  }

}
