package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "\"user\"")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotBlank(message = "Username is mandatory")
    @Column(name = "username", length = 125)
    private String username;

    @NotBlank(message = "Password is mandatory")
    @Column(name = "password", length = 125)
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+{}\":;'?/<>,.]).{8,}$",
            message = "Password must contain at least 8 characters, 1 digit, 1 special character and 1 uppercase letter")
    private String password;

    @NotBlank(message = "FullName is mandatory")
    @Column(name = "fullname", length = 125)
    private String fullname;

    @NotBlank(message = "Role is mandatory")
    @Column(name = "role", length = 125)
    private String role;
}
