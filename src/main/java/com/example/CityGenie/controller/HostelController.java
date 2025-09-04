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

import com.example.CityGenie.entity.Hostel;
import com.example.CityGenie.service.HostelService;

@RestController
@RequestMapping("/hostels")
public class HostelController {

    @Autowired
    private HostelService hostelService;

    @PostMapping("/add")
    public Hostel addHostel(@RequestBody Hostel hostel) {
        return hostelService.addHostel(hostel);
    }

    @GetMapping("/all")
    public List<Hostel> getAllHostel() {
        return hostelService.getAllHostels();
    }

    @GetMapping("/type/{type}")
    public List<Hostel> getHostelByType(@PathVariable String type) {
        return hostelService.getHostelByType(type);
    }

    @GetMapping("/location")
    public List<Hostel> getHostelByLocation(@RequestParam String location) {
        return hostelService.getHostelByLocation(location);
    }

}
