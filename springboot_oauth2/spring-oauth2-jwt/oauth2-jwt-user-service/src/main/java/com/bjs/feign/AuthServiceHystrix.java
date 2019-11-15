package com.bjs.feign;

import com.bjs.pojo.JWT;

import org.springframework.stereotype.Component;

/**
 * @Description
 * @Date 2019-09-23 15:42:14
 * @Author BianJiashuai
 */
@Component
public class AuthServiceHystrix implements AuthServiceClient {
    @Override
    public JWT getToken(String authorization, String grant_type, String username, String password) {
        return null;
    }
}
