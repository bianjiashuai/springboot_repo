package com.bjs.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

@EnableEurekaClient
@EnableResourceServer
@SpringBootApplication
public class OauthServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(OauthServiceApplication.class, args);
    }

    @Autowired
//  @Qualifier("dataSource")
    private DataSource dataSource;

    @Configuration
    @EnableAuthorizationServer  // 开启授权服务功能
    protected class OAuth2AuthorizationConfig extends AuthorizationServerConfigurerAdapter {

        JdbcTokenStore tokenStore = new JdbcTokenStore(dataSource);

        @Autowired
        private AuthenticationManager manager;
        @Autowired
        private UserDetailsService userDetailsService;

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            // 将客户端端信息存储在内存中
            clients.inMemory()
//            clients.jdbc(dataSource)
                    // 创建一个 clientId 为 browser 的客户端
                    .withClient("browser")
                    // 验证类型为 refresh_token 和 password
                    .authorizedGrantTypes("refresh_token", "password")
                    // 客户端域为 ui
                    .scopes("ui")
                    .and()
                    // 创建一个 clientId 为 service-hi 的客户端
                    .withClient("service-hi")
                    .secret("123456")
                    .authorizedGrantTypes("client_credentials", "refresh_token", "password")
                    .scopes("server");
        }

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
            endpoints.tokenStore(tokenStore)
                    .authenticationManager(manager)
                    .userDetailsService(userDetailsService);
        }

        @Override
        public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
            security.tokenKeyAccess("permitAll()")
                    .checkTokenAccess("isAuthenticated()");
        }
    }
}
