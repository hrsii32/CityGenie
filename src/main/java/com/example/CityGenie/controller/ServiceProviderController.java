package com.example.CityGenie.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.CityGenie.entity.ServiceProvider;
import com.example.CityGenie.service.ServiceProviderService;

@RestController
@RequestMapping("/service_provider")
public class ServiceProviderController {

    @Autowired
    private ServiceProviderService serviceProviderService;

    @PostMapping("/add")
    public ServiceProvider addService(@RequestBody ServiceProvider serviceProvider) {
        return serviceProviderService.addServiceProvider(serviceProvider);
    }

    @GetMapping("/all")
    public List<ServiceProvider> getAllService() {
        return serviceProviderService.getAllServiceProviders();
    }

    @GetMapping("/type/{type}")
    public List<ServiceProvider> getServiceByType(@PathVariable String type) {
        return serviceProviderService.getByType(type);
    }

    @GetMapping("/location")
    public List<ServiceProvider> getServiceByLocation(@RequestParam String location) {
        return serviceProviderService.getByLocation(location);
    }

}
