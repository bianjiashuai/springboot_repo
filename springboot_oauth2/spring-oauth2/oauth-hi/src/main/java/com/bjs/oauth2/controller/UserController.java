package com.bjs.oauth2.controller;

import com.bjs.oauth2.entity.User;
import com.bjs.oauth2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {

  @Autowired
  UserService userService;

  @RequestMapping(value = "register", method = RequestMethod.POST)
  public User create(@RequestParam("username") String username, @RequestParam("password") String password) {
    return userService.create(username, password);
  }
}
