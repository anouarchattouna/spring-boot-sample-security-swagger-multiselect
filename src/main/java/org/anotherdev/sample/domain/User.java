package org.anotherdev.sample.domain;

import java.util.Set;

/**
 * @author Anouar
 */
public class User {

  private String username;

  private String password;

  private Set<String> userRoles;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Set<String> getUserRoles() {
    return userRoles;
  }

  public void setUserRoles(Set<String> userRoles) {
    this.userRoles = userRoles;
  }

  @Override
  public String toString() {
    return "User [username=" + username + ", userRoles=" + userRoles + "]";
  }

}
