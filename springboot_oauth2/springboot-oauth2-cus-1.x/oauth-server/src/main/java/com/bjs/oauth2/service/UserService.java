package com.bjs.oauth2.service;

import com.bjs.oauth2.entity.User;

public interface UserService {

    User create(String username, String password);
}
