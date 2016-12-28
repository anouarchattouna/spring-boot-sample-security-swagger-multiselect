package org.anotherdev.sample.service;

import org.anotherdev.sample.domain.User;

/**
 * @author Anouar
 */
public interface UserRepository {
  public User findByUsername(final String username);
}
