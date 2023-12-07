package com.nnk.springboot.mock;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.List;
import java.util.stream.Stream;

@RequiredArgsConstructor
class WithMockPrincipalSecurityContextFactory implements WithSecurityContextFactory<WithMockPrincipal> {

    @Override
    public SecurityContext createSecurityContext(WithMockPrincipal principal) {
        SecurityContext context = SecurityContextHolder.getContext();
        List<GrantedAuthority> grantedAuthorities = Stream.of(principal.authorities())
                .map(role -> "ROLE_" + role.toUpperCase())
                .map(authority -> (GrantedAuthority) new SimpleGrantedAuthority(authority))
                .toList();
        User user = new User("test", "password", grantedAuthorities);
        Authentication auth = new UsernamePasswordAuthenticationToken(user, null, grantedAuthorities);
        context.setAuthentication(auth);
        return context;
    }
}