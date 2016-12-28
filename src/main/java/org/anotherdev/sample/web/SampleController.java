package org.anotherdev.sample.web;

import java.util.Collections;
import java.util.Set;

import org.anotherdev.sample.domain.ApiInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Anouar
 */
@Controller
public class SampleController {

  /**
   * The classic logger for this class.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(SampleController.class);

  @Autowired
  private ApiDocumentationHelper apiDocumentationHelper;

	@RequestMapping("/")
	public String root() {
    return "redirect:/specification";
	}

  @RequestMapping("/specifications")
  public String specification(Authentication authentication, Model model) {
    if (authentication.getAuthorities().isEmpty()) {
      model.addAttribute("apis", Collections.emptyList());
      LOGGER.debug("No available API for user {}", authentication.getName());
    }
    Set<ApiInfo> apis = apiDocumentationHelper.getAllowedApisPerCredentials(authentication.getAuthorities());
    model.addAttribute("apis", apis);
    return "specification";
  }

  @RequestMapping("/signin")
  public String signin() {
    return "signin";
  }

  @RequestMapping("/signin-error")
  public String signinError(Model model) {
    model.addAttribute("signinError", true);
    return "signin";
  }

  /**
   * should not be exposed (or allow it only for ADMIN)
   */
  @RequestMapping("/hash/bcrypt/{password}")
  @ResponseBody
  public String hash(@PathVariable String password) {
    PasswordEncoder enc = new BCryptPasswordEncoder();
    return enc.encode(password);
  }

}
