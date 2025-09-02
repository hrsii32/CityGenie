package com.example.CityGenie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.CityGenie.entity.User;
import com.example.CityGenie.service.AuthService;

@RestController
@RequestMapping("/")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public String signup(@RequestBody User user){
        return authService.signup(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody User user){
        return authService.login(user.getEmail(), user.getPassword());

}
