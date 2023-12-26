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

    /**
     * Configures the security filter chain for the Spring Security framework.
     *
     * <p>This method sets up various security configurations using the {@link HttpSecurity} object.
     * It defines the security constraints for different URL patterns and the authentication mechanisms
     * for the application.</p>
     *
     * <ul>
     *     <li>CSRF protection is disabled.</li>
     *     <li>Specifies URL patterns for access control:
     *       <ul>
     *         <li>Permits all requests to resources under '/css/' with a GET method.</li>
     *         <li>Restricts access to URLs for delete update or add entity, and get user list
     *         to users with 'ADMIN' authority.</li>
     *         <li>Requires authentication for all other requests.</li>
     *       </ul>
     *     </li>
     *     <li>Configures form-based login:
     *       <ul>
     *         <li>Custom login page set to '/login'.</li>
     *         <li>Redirects to '/bidList/list' upon successful login.</li>
     *         <li>Redirects to '/login?error=true' after login failure.</li>
     *       </ul>
     *     </li>
     *     <li>Configures logout:
     *       <ul>
     *         <li>Sets logout URL to '/app-logout'.</li>
     *         <li>Redirects to '/login' after successful logout.</li>
     *       </ul>
     *     </li>
     *     <li>Sets a custom page for access denied errors ('/forbidden').</li>
     * </ul>
     *
     * @param http the {@link HttpSecurity} to configure
     * @return the configured {@link SecurityFilterChain}
     * @throws Exception if an error occurs during the configuration
     */
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
