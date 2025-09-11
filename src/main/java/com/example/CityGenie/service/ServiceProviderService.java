package com.example.CityGenie.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.CityGenie.dto.ServiceProviderResponse;
import com.example.CityGenie.entity.ServiceProvider;
import com.example.CityGenie.repository.ServiceProviderRepository;

@Service
public class ServiceProviderService {

    @Autowired
    private ServiceProviderRepository serviceProviderRepo;

    public ServiceProviderResponse addServiceProvider(ServiceProvider serviceProvider) {
        ServiceProvider saved = serviceProviderRepo.save(serviceProvider);
        return mapToResponse(saved);
    }

    public List<ServiceProviderResponse> getAllServiceProviders() {
        return serviceProviderRepo.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public ServiceProviderResponse getServiceById(Long id) {
        ServiceProvider provider = serviceProviderRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Service Provider Not Found"));
        return mapToResponse(provider);
    }

    public List<ServiceProviderResponse> getByType(String type) {
        return serviceProviderRepo.findByTypeAndAvailable(type, true)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<ServiceProviderResponse> getByLocation(String location) {
        return serviceProviderRepo.findByLocationContainingAndAvailable(location, true)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public ServiceProviderResponse updateProvider(Long id, ServiceProvider updateProvider) {
        ServiceProvider provider = serviceProviderRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Service Provider Not Found"));

        provider.setName(updateProvider.getName());
        provider.setType(updateProvider.getType());
        provider.setAvailable(updateProvider.isAvailable());
        provider.setLocation(updateProvider.getLocation());

        ServiceProvider updated = serviceProviderRepo.save(provider);
        return mapToResponse(updated);
    }

    public void deleteServiceProvider(Long id) {
        ServiceProvider provider = serviceProviderRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Service Provider Not Found"));
        serviceProviderRepo.delete(provider);
    }

    private ServiceProviderResponse mapToResponse(ServiceProvider provider) {
        return new ServiceProviderResponse(
                provider.getId(),
                provider.getName(),
                provider.getType(),
                provider.getLocation(),
                provider.isAvailable(),
                provider.getOwner() != null ? provider.getOwner().getName() : null,
                provider.getOwner() != null ? provider.getOwner().getEmail() : null
        );
    }

}
