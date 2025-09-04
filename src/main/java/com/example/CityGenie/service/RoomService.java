package com.example.CityGenie.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.CityGenie.entity.Room;
import com.example.CityGenie.repository.RoomRepository;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepo;

    public Room addRoom(Room room) {
        return roomRepo.save(room);
    }

    public List<Room> getAllRooms() {
        return roomRepo.findAll();
    }

    public List<Room> getRoomByType(String type) {
        return roomRepo.findByTypeAndAvailable(type, true);
    }

    public List<Room> getRoomsByLocation(String location) {
        return roomRepo.findByLocationContainingAndAvailable(location, true);
    }

}
