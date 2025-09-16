package com.example.CityGenie.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.multipart.MultipartFile;

import com.example.CityGenie.dto.ServiceProviderResponse;
import com.example.CityGenie.entity.ServiceProvider;
import com.example.CityGenie.service.ServiceProviderService;
import com.example.CityGenie.service.StorageService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/services") // changed from /service_provider
public class ServiceProviderController {

    @Autowired
    private ServiceProviderService serviceProviderService;

    @Autowired
    private StorageService storageService;

    @PostMapping("/add")
    public ServiceProviderResponse addService(@RequestBody @Valid ServiceProvider serviceProvider) {
        return serviceProviderService.addServiceProvider(serviceProvider);
    }

    @GetMapping("/all")
    public List<ServiceProviderResponse> getAllService() {
        return serviceProviderService.getAllServiceProviders();
    }

    @GetMapping("/get/{id}")
    public ServiceProviderResponse getServiceById(@PathVariable Long id) {
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

    @PreAuthorize("hasAnyRole('PROVIDER, ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<ServiceProviderResponse> updateService(
            @PathVariable Long id,
            @RequestBody ServiceProvider service) {
        return ResponseEntity.ok(serviceProviderService.updateProvider(id, service));
    }

    @PreAuthorize("hasAnyRole('PROVIDER, ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteService(@PathVariable Long id) {
        serviceProviderService.deleteServiceProvider(id);
        return ResponseEntity.ok("Service deleted successfully");
    }

    public ResponseEntity<String> uploadImage(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {

        try {

            String imageUrl = storageService.store(file);

            serviceProviderService.updateImageUrl(id, imageUrl);

            return ResponseEntity.ok("Image uploaded successfully: " + imageUrl);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Upload failed: " + e.getMessage());
        }
    }

}
