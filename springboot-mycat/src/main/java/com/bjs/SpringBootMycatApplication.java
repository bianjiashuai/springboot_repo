package com.bjs;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.jemcocloud.swagger.annotation.EnableZwwlSwagger;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @Description
 * @Date 2019-11-09 14:39:07
 * @Author BianJiashuai
 */
@EnableZwwlSwagger
@MapperScan("com.bjs.mapper")
@SpringBootApplication
public class SpringBootMycatApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootMycatApplication.class, args);
    }

    // 这个一定要有, 否则分页不生效
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
