package com.hatchways.finance.controller;

import com.hatchways.finance.exception.BadRequestException;
import com.hatchways.finance.exception.NotFoundException;
import com.hatchways.finance.exception.UnauthorizedException;
import com.hatchways.finance.model.User;
import com.hatchways.finance.schema.AuthRequestBody;
import com.hatchways.finance.schema.AuthResponse;
import com.hatchways.finance.service.UserService;
import com.hatchways.finance.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class AuthController {

  private final UserService userService;
  private final Environment env;

  @Autowired
  public AuthController(UserService userService, Environment env) {
    this.userService = userService;
    this.env = env;
  }

  /**
   * Create a new user in the database.
   */
  @PostMapping("/signup")
  public ResponseEntity<AuthResponse> register(
      @Valid @RequestBody AuthRequestBody authRequestBody) {
    User existingUser = this.userService.getByEmail(authRequestBody.getEmail());
    if (existingUser != null) {
      throw new BadRequestException("Email already registered");
    }

    User user =
        this.userService.createUser(authRequestBody.getEmail(), authRequestBody.getPassword());

    String token = AuthUtil.generateToken(user.getEmail(), env.getProperty("SESSION_SECRET"));
    AuthResponse response = new AuthResponse(token, user);
    return ResponseEntity.ok(response);
  }

  /**
   * Validate a user's login attempt.
   */
  @PostMapping("/login")
  public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequestBody authRequestBody) {
    User user = this.userService.getByEmail(authRequestBody.getEmail());
    if (user == null) {
      throw new NotFoundException("User");
    }

    if (!user.verifyPassword(authRequestBody.getPassword())) {
      throw new UnauthorizedException("Incorrect email or password");
    }

    String token = AuthUtil.generateToken(user.getEmail(), env.getProperty("SESSION_SECRET"));
    AuthResponse response = new AuthResponse(token, user);
    return ResponseEntity.ok(response);
  }
}
