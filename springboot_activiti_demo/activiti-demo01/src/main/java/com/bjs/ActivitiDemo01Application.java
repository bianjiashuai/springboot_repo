package com.bjs;

import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Description
 * @Date 2019-10-28 10:34:46
 * @Author BianJiashuai
 */
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class ActivitiDemo01Application {

    public static void main(String[] args) {
        SpringApplication.run(ActivitiDemo01Application.class, args);
    }
}
