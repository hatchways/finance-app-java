package com.hatchways.finance.service;

import com.hatchways.finance.model.User;
import com.hatchways.finance.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  @Autowired private UserRepository userRepository;

  @Override
  public User getByEmail(String email) {
    return this.userRepository.findByEmail(email);
  }

  @Override
  public User createUser(String email, String password) {
    User user = new User(email, password);
    this.userRepository.save(user);
    this.userRepository.flush();
    return user;
  }
}
