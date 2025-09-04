package com.example.CityGenie.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.CityGenie.entity.ServiceProvider;
import com.example.CityGenie.repository.ServiceProviderRepository;

@Service
public class ServiceProviderService {

    @Autowired
    private ServiceProviderRepository serviceProviderRepo;

    public ServiceProvider addServiceProvider(ServiceProvider serviceProvider) {
        return serviceProviderRepo.save(serviceProvider);
    }

    public List<ServiceProvider> getAllServiceProviders() {
        return serviceProviderRepo.findAll();
    }

    public List<ServiceProvider> getByType(String type) {
        return serviceProviderRepo.findByTypeAndAvailable(type, true);
    }

    public List<ServiceProvider> getByLocation(String location) {
        return serviceProviderRepo.findBylocationContainingAndAvailable(location, true);
    }

}
