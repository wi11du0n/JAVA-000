package com.example.springweek05.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Bean
    public User user() {
        User u = new User();
        u.setId(1);
        u.setName("test bean user");
        return u;
    }
}
