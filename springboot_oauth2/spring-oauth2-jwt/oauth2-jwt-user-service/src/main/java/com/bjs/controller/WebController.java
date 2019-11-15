package com.bjs.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @Description
 * @Date 2019-09-23 16:00:15
 * @Author BianJiashuai
 */
@RestController
public class WebController {

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping(value = "foo", method = RequestMethod.GET)
    public String getFoo() {
        return "i'm foo, " + UUID.randomUUID().toString() + " --->>>must be ROLE_ADMIN";
    }

    @RequestMapping(value = "hi", method = RequestMethod.GET)
    public String hi() {
        return "hi, " + UUID.randomUUID().toString();
    }
}
