package com.nnk.springboot.services;

import com.nnk.springboot.domain.Login;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Security;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserService userService;

    private final PasswordEncoder encoder;

    public void login(Login login) {
        Optional<User> optionalUser = userService.getUserByUsername(login.getUsername());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            String password = user.getPassword();
            if (encoder.matches(login.getPassword(), password)) {
                SecurityContextHolder.getContext().setAuthentication(
                        new UsernamePasswordAuthenticationToken(
                                user.getUsername(),
                                user.getPassword()
                        )
                );
            }
        }
    }
}
