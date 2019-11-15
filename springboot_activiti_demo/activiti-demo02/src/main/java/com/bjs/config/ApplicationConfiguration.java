package com.bjs.config;

import org.apache.catalina.mbeans.MemoryUserDatabaseMBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description
 * @Date 2019-10-29 15:39:19
 * @Author BianJiashuai
 */
@Slf4j
@Configuration
public class ApplicationConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService());
    }

    @Bean
    public UserDetailsService myUserDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        JdbcUserDetailsManager manager = new JdbcUserDetailsManager(dataSource);
        String[][] userGroupsAndRoles = {
                {"zs", "111111", "ROLE_ACTIVITI_USER", "GROUP_activitiTeam"},
                {"ls", "111111", "ROLE_ACTIVITI_USER", "GROUP_activitiTeam"},
                {"ww", "111111", "ROLE_ACTIVITI_USER", "GROUP_activitiTeam"},
                {"other", "111111", "ROLE_ACTIVITI_USER", "GROUP_otherTeam"},
                {"admin", "111111", "ROLE_ACTIVITI_ADMIN"}
        };

        for (String[] user : userGroupsAndRoles) {
            List<String> authorities = Arrays.asList(Arrays.copyOfRange(user, 2, user.length));
            log.info("> Registering new user: " + user[0] + " with the following Authorities[" + authorities + "]");
            manager.createUser(new User(user[0], passwordEncoder().encode(user[1]),
                    authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())));
        }

        return manager;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests().anyRequest().authenticated().and().httpBasic();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
