package com.bjs.oauth2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class OauthEurekaServerApplication {
  public static void main(String[] args) {
    SpringApplication.run(OauthEurekaServerApplication.class, args);
  }
}
