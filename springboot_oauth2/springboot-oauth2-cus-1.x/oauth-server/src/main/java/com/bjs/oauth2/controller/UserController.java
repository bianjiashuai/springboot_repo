package com.bjs.oauth2.controller;

import com.bjs.oauth2.entity.User;
import com.bjs.oauth2.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    JdbcClientDetailsService clientDetailsService;


    @RequestMapping(value = "current/{clientSecret}/{reqUri}/{clientId}", method = RequestMethod.GET)
    public Principal getUser(Principal principal,
                             @PathVariable("clientSecret") String clientSecret,
                             @PathVariable("reqUri") String reqUri,
                             @PathVariable("clientId") String clientId) {
        System.out.println("我被调用了");
        System.out.println(clientId);
        System.out.println(clientSecret);
        System.out.println(reqUri);

        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);
        System.out.println(clientDetails);

        return principal;
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public User create(@RequestParam("username") String username, @RequestParam("password") String password) {
        return userService.create(username, password);
    }

    @GetMapping(value = "info")
    public Object userInfo(Principal principal) {
        if (principal instanceof OAuth2Authentication) {
            OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) principal;
            return oAuth2Authentication.getUserAuthentication().getPrincipal();
        }
        return principal;
    }
}
