package com.example.CityGenie.dto;

import com.example.CityGenie.entity.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignUpRequest {

    @NotBlank(message = "Name is Required")
    private String name;

    @Email(message = "Invalid Email Format")
    @NotBlank(message = "Email is Required")
    private String email;

    @Size(min = 8, message = "password Must be 8 characters")
    @NotBlank
    private String password;

    private Role role;

}
