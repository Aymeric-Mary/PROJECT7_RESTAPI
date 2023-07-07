package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
@Component
@RequiredArgsConstructor
public class GlobalModelAttributeAdvice {

    private final UserService userService;

    @ModelAttribute
    public void addConnectedUserName(Model model) {
        User user = userService.getConnectedUser();
        model.addAttribute("username", user.getUsername());
    }
}
