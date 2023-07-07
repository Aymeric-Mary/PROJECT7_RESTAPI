package com.nnk.springboot.mock;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class WithMockPrincipalSecurityContextFactory implements WithSecurityContextFactory<WithMockPrincipal> {

    private final UserRepository userRepository;

    @Override
    public SecurityContext createSecurityContext(WithMockPrincipal principal) {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = new UsernamePasswordAuthenticationToken(createUser().getUsername(), null, List.of());
        context.setAuthentication(auth);
        return context;
    }

    private User createUser() {
        return userRepository.save(
                User.builder()
                        .username("user")
                        .password("password")
                        .fullname("fullname")
                        .role("USER")
                        .build()
        );
    }
}