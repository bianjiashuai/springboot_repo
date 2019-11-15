package com.bjs.oauth2.service.impl;

import com.bjs.oauth2.entity.User;
import com.bjs.oauth2.repository.UserRepository;
import com.bjs.oauth2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

  @Autowired
  UserRepository userRepository;

  @Override
  public User create(String username, String password) {
    User user = new User();
    user.setUsername(username);
    String encPwd = encoder.encode(password);
    user.setPassword(encPwd);
    return userRepository.save(user);
  }

}
