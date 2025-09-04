package com.example.CityGenie.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.CityGenie.entity.Hostel;
import com.example.CityGenie.repository.HostelRepository;

@Service
public class HostelService {

    @Autowired
    private HostelRepository hostelRepo;

    public Hostel addHostel(Hostel hostel) {
        return hostelRepo.save(hostel);
    }

    public List<Hostel> getAllHostels() {
        return hostelRepo.findAll();
    }

    public List<Hostel> getHostelByType(String type) {
        return hostelRepo.findByTypeAndAvailable(type, true);
    }

    public List<Hostel> getHostelByLocation(String location) {
        return hostelRepo.findByLocationContainingAndAvailable(location, true);
    }

}
