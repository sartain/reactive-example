package com.example.demo.premierleague.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class ScoreAuth {

    /**
     * Setup a reactive user details service
     * @return User details service with username and password
     */

    @Bean
    MapReactiveUserDetailsService authentication() {
        UserDetails adminUser = User.withDefaultPasswordEncoder().username("user").password("password").roles("ADMIN").build();
        return new MapReactiveUserDetailsService(adminUser);
    }

    /**
     * Setup authorization mechanism for Http requests
     * @param http Server security to build
     * @return Filter chain for securing endpoints
     */

    @Bean
    SecurityWebFilterChain authorization(ServerHttpSecurity http) {
        return http
                .csrf(e -> e.disable())
                .httpBasic(Customizer.withDefaults())
                .authorizeExchange(
                        ae -> ae.pathMatchers("/auth").authenticated()
                        .anyExchange().permitAll()
                )
                .build();
    }

}
