package com.example.CityGenie.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.CityGenie.dto.ServiceProviderResponse;
import com.example.CityGenie.entity.ServiceProvider;
import com.example.CityGenie.service.ServiceProviderService;

@RestController
@RequestMapping("/service_provider")
public class ServiceProviderController {

    @Autowired
    private ServiceProviderService serviceProviderService;

    @PreAuthorize("hasRole('PROVIDER')")
    @PostMapping("/add")
    public ServiceProviderResponse addService(@RequestBody ServiceProvider serviceProvider) {
        return serviceProviderService.addServiceProvider(serviceProvider);
    }

    @GetMapping("/all")
    public List<ServiceProviderResponse> getAllService() {
        return serviceProviderService.getAllServiceProviders();
    }

    @GetMapping("get/{id}")
    public ServiceProviderResponse getServiceProviderById(@PathVariable Long id) {
        return serviceProviderService.getServiceById(id);
    }

    @GetMapping("/type/{type}")
    public List<ServiceProviderResponse> getServiceByType(@PathVariable String type) {
        return serviceProviderService.getByType(type);
    }

    @GetMapping("/location")
    public List<ServiceProviderResponse> getServiceByLocation(@RequestParam String location) {
        return serviceProviderService.getByLocation(location);
    }

    @PreAuthorize("hasRole('PROVIDER')")
    @PutMapping("/update/{id}")
    public ResponseEntity<ServiceProviderResponse> updateService(
            @PathVariable Long id,
            @RequestBody ServiceProvider service) {
        return ResponseEntity.ok(serviceProviderService.updateProvider(id, service));
    }

    @PreAuthorize("hasRole('PROVIDER')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteService(@PathVariable Long id) {
        serviceProviderService.deleteServiceProvider(id);
        return ResponseEntity.ok("Service deleted successfully");
    }
}
