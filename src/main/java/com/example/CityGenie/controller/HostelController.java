package com.example.CityGenie.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.CityGenie.dto.HostelRequest;
import com.example.CityGenie.dto.HostelResponse;
import com.example.CityGenie.entity.Hostel;
import com.example.CityGenie.entity.User;
import com.example.CityGenie.repository.UserRepository;
import com.example.CityGenie.service.HostelService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/hostels")
public class HostelController {

    @Autowired
    private HostelService hostelService;

    @Autowired
    private UserRepository userRepo;

    @PreAuthorize("hasRole('PROVIDER, ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<HostelResponse> addHostel(@Valid @RequestBody HostelRequest request, Authentication auth) {
        String email = auth.getName();
        User owner = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        Hostel hostel = new Hostel();
        hostel.setName(request.getName());
        hostel.setLocation(request.getLocation());
        hostel.setType(request.getType());
        hostel.setRentPerMonth(request.getRent());
        hostel.setAvailable(request.isAvailable());
        hostel.setOwner(owner);

        HostelResponse response = hostelService.addHostel(hostel);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<HostelResponse>> getAllHostels() {
        return ResponseEntity.ok(hostelService.getAllHostels());
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<HostelResponse>> getHostelByType(@PathVariable String type) {
        return ResponseEntity.ok(hostelService.getHostelByType(type));
    }

    @GetMapping("/location")
    public ResponseEntity<List<HostelResponse>> getHostelByLocation(@RequestParam String location) {
        return ResponseEntity.ok(hostelService.getHostelByLocation(location));
    }

    @PreAuthorize("hasRole('OWNER')")
    @PutMapping("/update/{id}")
    public ResponseEntity<HostelResponse> updateHostel(@PathVariable Long id, @RequestBody HostelRequest request) {
        Hostel hostel = new Hostel();
        hostel.setName(request.getName());
        hostel.setLocation(request.getLocation());
        hostel.setType(request.getType());
        hostel.setRentPerMonth(request.getRent());
        hostel.setAvailable(request.isAvailable());

        HostelResponse response = hostelService.updateHostel(id, hostel);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('OWNER')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteHostel(@PathVariable Long id) {
        hostelService.deleteHostel(id);
        return ResponseEntity.ok("Hostel deleted successfully");
    }
}
