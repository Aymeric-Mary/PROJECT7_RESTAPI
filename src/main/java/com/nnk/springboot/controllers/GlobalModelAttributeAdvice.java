package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Optional;

@ControllerAdvice
@Component
@RequiredArgsConstructor
public class GlobalModelAttributeAdvice {

    private final UserService userService;

    @ModelAttribute
    public void addConnectedUserName(Model model) {
        Optional<User> user = userService.getConnectedUser();
        if (user.isEmpty()) {
            return;
        }
        model.addAttribute("username", user.get().getUsername());
        model.addAttribute("user", user.get());
    }
}
