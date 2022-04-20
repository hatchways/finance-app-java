package com.hatchways.finance.controller;

import com.hatchways.finance.exception.CredentialsException;
import com.hatchways.finance.model.User;
import com.hatchways.finance.service.UserService;
import com.hatchways.finance.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

  private final UserService userService;
  private final Environment env;

  @Autowired
  public UserController(UserService userService, Environment env) {
    this.userService = userService;
    this.env = env;
  }

  /**
   * Authenticate and return the current user
   */
  @GetMapping
  public ResponseEntity<User> getAuthenticatedUser(
      @RequestHeader("Authorization") String authorization) {
    String email = AuthUtil.getCurrentUserEmail(authorization, env.getProperty("SESSION_SECRET"));
    User user = this.userService.getByEmail(email);
    if (user == null) {
      throw new CredentialsException();
    }

    return ResponseEntity.ok(user);
  }
}
