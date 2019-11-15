package com.bjs.service;

import com.bjs.entity.User;
import com.bjs.ex.UserLoginException;
import com.bjs.feign.AuthServiceClient;
import com.bjs.pojo.JWT;
import com.bjs.pojo.UserLoginDto;
import com.bjs.repository.UserRepository;
import com.bjs.utils.BPwdEncoderUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceDetail implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthServiceClient client;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public User insertUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        return userRepository.save(user);
    }

    public UserLoginDto login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (null == user) {
            throw new UserLoginException("error username");
        }
        if (!BPwdEncoderUtil.matches(password, user.getPassword())) {
            throw new UserLoginException("error password");
        }
        //

        JWT jwt = client.getToken("Basic dXNlci1zZXJ2aWNlOjEyMzQ1Ng==", "password", username, password);
        if (null == jwt) {
            throw new UserLoginException("error internal");
        }

        UserLoginDto userLoginDto = new UserLoginDto();
        userLoginDto.setJwt(jwt);
        userLoginDto.setUser(user);
        return userLoginDto;
    }
}
