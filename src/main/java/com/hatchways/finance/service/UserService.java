package com.hatchways.finance.service;

import com.hatchways.finance.model.User;

public interface UserService {
  /**
   * Get a User by their email
   * @param email the email to look for
   * @return a User
   */
  User getByEmail(String email);

  /**
   * Create a user with the given email and password
   * @param email the user's email
   * @param password the user's password
   * @return the created User
   */
  User createUser(String email, String password);
}
