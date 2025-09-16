package com.example.CityGenie.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.CityGenie.dto.HostelResponse;
import com.example.CityGenie.entity.Hostel;
import com.example.CityGenie.exception.ResourceNotFoundException;
import com.example.CityGenie.repository.HostelRepository;

@Service
public class HostelService {

    @Autowired
    private HostelRepository hostelRepo;

    public HostelResponse addHostel(Hostel hostel) {
        Hostel savedHostel = hostelRepo.save(hostel);
        return mapToResponse(savedHostel);
    }

    public List<HostelResponse> getAllHostels() {
        return hostelRepo.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public HostelResponse getHostelById(Long id) {
        Hostel hostel = hostelRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hostel not found with id: " + id));
        return mapToResponse(hostel);
    }

    public List<HostelResponse> getHostelByType(String type) {
        return hostelRepo.findByTypeAndAvailable(type, true)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<HostelResponse> getHostelByLocation(String location) {
        return hostelRepo.findByLocationContainingAndAvailable(location, true)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public HostelResponse updateHostel(Long id, Hostel updatedHostel) {
        Hostel hostel = hostelRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hostel Not Found" + id));

        hostel.setName(updatedHostel.getName());
        hostel.setLocation(updatedHostel.getLocation());
        hostel.setAvailable(updatedHostel.isAvailable());
        hostel.setType(updatedHostel.getType());

        Hostel savedHostel = hostelRepo.save(hostel);
        return mapToResponse(savedHostel);
    }

    public void deleteHostel(Long id) {
        Hostel hostel = hostelRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hostel Not Found"));
        hostelRepo.delete(hostel);
    }

    public void updateImageUrl(Long hostelId, String imageUrl) {
        Hostel hostel = hostelRepo.findById(hostelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hostel not found"));
        hostel.setImageUrl(imageUrl);
        hostelRepo.save(hostel);
    }

    private HostelResponse mapToResponse(Hostel hostel) {
        return new HostelResponse(
                hostel.getId(),
                hostel.getName(),
                hostel.getLocation(),
                hostel.getType(),
                hostel.getRentPerMonth(),
                hostel.isAvailable(),
                hostel.getOwner() != null ? hostel.getOwner().getName() : null,
                hostel.getOwner() != null ? hostel.getOwner().getEmail() : null
        );
    }
}
