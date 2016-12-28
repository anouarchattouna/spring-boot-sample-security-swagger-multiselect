package org.anotherdev.sample.web;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.anotherdev.sample.domain.ApiInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * @author Anouar
 *
 */
@Component

public final class ApiDocumentationHelper implements EnvironmentAware {

  /**
   * The classic logger for this class.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(ApiDocumentationHelper.class);

  /**
   * The property resolver bean
   */
  private RelaxedPropertyResolver relaxedPropertyResolver;

  /*
   * (non-Javadoc)
   * 
   * @see org.springframework.context.EnvironmentAware#setEnvironment(org.springframework.core.env.Environment)
   */
  @Override
  public void setEnvironment(Environment environment) {
    this.relaxedPropertyResolver = new RelaxedPropertyResolver(environment, "org.anotherdev.documentation.api.");
  }

  public Set<ApiInfo> getAllowedApisPerCredentials(final Collection<? extends GrantedAuthority> authorities) {
    final Map<String, Object> properties = relaxedPropertyResolver.getSubProperties(null);
    if (CollectionUtils.isEmpty(properties)) {
      return Collections.emptySet();
    }
    Set<ApiInfo> apiInfos = new HashSet<ApiInfo>();
    for (GrantedAuthority grantedAuthority : authorities) {
      ApiInfo apiInfo = getAllowedApiPerRole(grantedAuthority.getAuthority());
      if (null != apiInfo) {
        apiInfos.add(apiInfo);
      }
    }
    return apiInfos;
  }

  private ApiInfo getAllowedApiPerRole(final String apiRole) {
    final Map<String, Object> properties = relaxedPropertyResolver.getSubProperties(null);
    if (CollectionUtils.isEmpty(properties)) {
      return null;
    }
    for (Entry<String, Object> prop : properties.entrySet()) {
      if (!StringUtils.isEmpty(prop.getKey()) && prop.getKey().endsWith(".role") && !StringUtils.isEmpty(prop.getValue().toString())
        && prop.getValue().toString().contains(apiRole)) {
        LOGGER.debug("{{}} allowed for {{}}", relaxedPropertyResolver.getProperty(prop.getKey().split(".role")[0] + ".key"), apiRole);
        return new ApiInfo(relaxedPropertyResolver.getProperty(prop.getKey().split(".role")[0] + ".key"),
          relaxedPropertyResolver.getProperty(prop.getKey().split(".role")[0] + ".value"));
      }
    }
    return null;
  }

}
