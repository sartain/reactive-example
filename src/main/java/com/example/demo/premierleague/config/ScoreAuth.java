package com.example.demo.premierleague.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class ScoreAuth {

    /**
     * Setup a reactive user details service
     * @return User details service with username and password
     */

    @Bean
    MapReactiveUserDetailsService authentication() {
        UserDetails adminUser = User.withUsername("user").password("password").passwordEncoder(p -> passwordEncoder().encode(p)).roles("ADMIN").build();
        return new MapReactiveUserDetailsService(adminUser);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
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
                .authorizeExchange()
                    .pathMatchers("/auth/**").authenticated()
                    .pathMatchers("/**").permitAll()
                .and()
                .build();
    }

}
