package com.bjs.controller;

import com.bjs.entity.User;
import com.bjs.pojo.UserLoginDto;
import com.bjs.service.UserServiceDetail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserServiceDetail userServiceDetail;

    @PostMapping("register")
    public User postUser(@RequestParam("username") String username, @RequestParam("password") String password) {
        return userServiceDetail.insertUser(username, password);
    }

    @PostMapping("login")
    public UserLoginDto login(@RequestParam("username") String username, @RequestParam("password") String passwrod) {
        // 参数判断，省略
        return userServiceDetail.login(username, passwrod);
    }
}
