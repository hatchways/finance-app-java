package com.hatchways.finance.controller;

import com.hatchways.finance.model.User;

import java.time.LocalDateTime;
import java.util.Random;

public class TestUtil {
  public static User getUser() {
    LocalDateTime now = LocalDateTime.now();
    User user = new User();
    user.setId(1);
    user.setEmail(String.format("%s@test.com", getAlphanumericString(10)));
    user.setCreatedAt(now);
    user.setUpdatedAt(now);
    return user;
  }

  public static String getAlphanumericString(int length) {
    Random random = new Random();
    return random
        .ints(97, 122)
        .limit(length)
        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
        .toString();
  }
}
