package com.sqli.chatUI.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .formLogin()
            .and()
            .authorizeRequests()
            .antMatchers(HttpMethod.GET, "/").hasRole("USER")
            .antMatchers(HttpMethod.GET, "/chat").hasRole("USER")
            .and()
            .exceptionHandling().accessDeniedPage("/accessDenied.jsp")
            .and()
            .csrf().disable();
    }
    @Bean
    public UserDetailsService userDetailsService() {
        User.UserBuilder users = User.builder();
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(users.username("user").password("{noop}password").roles("USER").build());
        manager.createUser(users.username("admin").password("{noop}password").roles("USER", "ADMIN").build());
        return manager;

    }
}