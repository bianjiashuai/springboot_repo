package com.bjs.feign;

import com.bjs.pojo.JWT;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Description
 * @Date 2019-09-23 15:34:11
 * @Author BianJiashuai
 */
@FeignClient(value = "uaa-service", fallback = AuthServiceHystrix.class)
public interface AuthServiceClient {

    @PostMapping(value = "/oauth/token")
    JWT getToken(@RequestHeader(value = "Authorization") String authorization,
                 @RequestParam("grant_type") String grant_type,
                 @RequestParam("username") String username,
                 @RequestParam("password") String password);
}
