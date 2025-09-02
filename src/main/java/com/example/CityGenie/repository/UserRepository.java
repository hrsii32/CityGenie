package com.example.CityGenie.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.CityGenie.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmailId(String email);

}
