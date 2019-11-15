package com.bjs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class OAuth2JwtEurekaApplication {
  public static void main(String[] args) {
    SpringApplication.run(OAuth2JwtEurekaApplication.class, args);
  }
}
