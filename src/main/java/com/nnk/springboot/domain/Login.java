package com.nnk.springboot.domain;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Login {
    @Column(name = "username", length = 125)
    private String username;

    @Column(name = "password", length = 125)
    private String password;
}
