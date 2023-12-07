package com.nnk.springboot.domain;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Login {
    @NotBlank(message = "Username is mandatory")
    @Column(name = "username", length = 125)
    private String username;

    @NotBlank(message = "Password is mandatory")
    @Column(name = "password", length = 125)
    private String password;
}
