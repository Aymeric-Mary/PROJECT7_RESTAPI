package com.nnk.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        request -> request
                                .requestMatchers(
                                        new AntPathRequestMatcher("/css/**", "GET")
                                )
                                .permitAll()
                                .requestMatchers(
                                        new AntPathRequestMatcher("/*/update/**"),
                                        new AntPathRequestMatcher("/*/delete/**"),
                                        new AntPathRequestMatcher("/*/add/**"),
                                        new AntPathRequestMatcher("/user/list")
                                ).hasAuthority("ADMIN")
                                .anyRequest().authenticated()
                )
                .formLogin(
                        login -> login
                                .loginPage("/login")
                                .defaultSuccessUrl("/bidList/list", true)
                                .failureUrl("/login?error=true")
                                .permitAll()
                )
                .logout(
                        logout -> logout
                                .logoutUrl("/app-logout")
                                .logoutSuccessUrl("/login")
                                .permitAll()
                )
                .exceptionHandling(
                        exception -> exception
                                .accessDeniedPage("/forbidden")
                );
        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
