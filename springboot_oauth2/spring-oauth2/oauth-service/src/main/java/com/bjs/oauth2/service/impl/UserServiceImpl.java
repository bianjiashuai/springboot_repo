package com.bjs.oauth2.service.impl;

import com.bjs.oauth2.entity.User;
import com.bjs.oauth2.repository.UserRepository;
import com.bjs.oauth2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

  private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

  @Autowired
  UserRepository userRepository;

  @Override
  public void create(User user) {
    User user1 = userRepository.findByUsername(user.getUsername());
    Assert.isNull(user1, "user already exist: " + user.getUsername());
    String encPwd = encoder.encode(user.getPassword());
    user.setPassword(encPwd);
    userRepository.save(user);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository.findByUsername(username);
  }
}
