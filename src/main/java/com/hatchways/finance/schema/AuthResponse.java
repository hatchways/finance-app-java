package com.hatchways.finance.schema;

import com.hatchways.finance.model.User;

import javax.validation.constraints.Email;

public class AuthResponse {
  private String token;

  @Email private User user;

  public AuthResponse(String token, User user) {
    this.user = user;
    this.token = token;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}
