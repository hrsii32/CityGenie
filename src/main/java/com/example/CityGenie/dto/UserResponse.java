package com.example.CityGenie.dto;

import com.example.CityGenie.entity.Role;

import lombok.Data;

@Data
public class UserResponse {

    private Long id;

    private String name;
    private String email;
    private Role role;

    public UserResponse(Long id, String name, String email, Role role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
    }

}
