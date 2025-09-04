package com.example.CityGenie.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.CityGenie.entity.ServiceProvider;

public interface ServiceProviderRepository extends JpaRepository<ServiceProvider, Long>{ 

    List<ServiceProvider> findByTypeAndAvailable(String type, boolean available);
    List<ServiceProvider> findBylocationContainingAndAvailable(String location, boolean available);

}
