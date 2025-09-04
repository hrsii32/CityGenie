package com.example.CityGenie.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.CityGenie.entity.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findByTypeAndAvailable(String type, boolean available);

    List<Room> findByLocationContainingAndAvailable(String location, boolean available);

}
