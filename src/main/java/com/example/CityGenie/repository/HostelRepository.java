package com.example.CityGenie.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.CityGenie.entity.Hostel;

public interface HostelRepository extends JpaRepository<Hostel, Long> {

    List<Hostel> findByTypeAndAvailable(String type, boolean available);

    List<Hostel> findByLocationContainingAndAvailable(String location, boolean avialable);

}
