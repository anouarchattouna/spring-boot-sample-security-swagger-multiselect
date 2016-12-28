package org.anotherdev.sample.service;

import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.anotherdev.sample.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.util.StringUtils;

/**
 * @author Anouar
 */
@Component
public class UserRepositoryAsPropertiesFile implements UserRepository {

  /**
   * The classic logger for this class.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsService.class);

  private RelaxedPropertyResolver relaxedPropertyResolver;

  public UserRepositoryAsPropertiesFile(Environment environment) {
    this.relaxedPropertyResolver = new RelaxedPropertyResolver(environment, "org.anotherdev.documentation.users.");
  }

  @Override
  public User findByUsername(String username) {
    Map<String, Object> properties = relaxedPropertyResolver.getSubProperties(username);
    if (CollectionUtils.isEmpty(properties)) {
      return null;
    }
    User user = new User();
    user.setUsername(username);
    for (Entry<String, Object> prop : properties.entrySet()) {
      if (!StringUtils.isEmpty(prop.getKey())) {
        if (prop.getKey().endsWith(".password") && !StringUtils.isEmpty(prop.getValue().toString())) {
          user.setPassword(prop.getValue().toString());
        }
        if (prop.getKey().endsWith(".role") && !StringUtils.isEmpty(prop.getValue().toString())) {
          String[] roles = prop.getValue().toString().split(",");
          Set<String> userRoles = new HashSet<String>();
          for (String r : roles) {
            userRoles.add(r);
          }
          user.setUserRoles(userRoles);
        }
      }
    }
    LOGGER.debug("User found {}", user);
    return user;
  }

}
