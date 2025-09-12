package com.example.CityGenie.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.CityGenie.dto.RoomResponse;
import com.example.CityGenie.entity.Room;
import com.example.CityGenie.exception.ResourceNotFoundException;
import com.example.CityGenie.repository.RoomRepository;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepo;

    public RoomResponse addRoom(Room room) {
        Room savedRoom = roomRepo.save(room);
        return mapToResponse(savedRoom);
    }

    public List<RoomResponse> getAllRooms() {
        return roomRepo.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public RoomResponse getRoomById(Long id) {
        Room room = roomRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + id));
        return mapToResponse(room);
    }

    public List<RoomResponse> getRoomByType(String type) {
        return roomRepo.findByTypeAndAvailable(type, true)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<RoomResponse> getRoomsByLocation(String location) {
        return roomRepo.findByLocationContainingAndAvailable(location, true)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public RoomResponse updateRoom(Long id, Room roomDetails) {
        Room room = roomRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Room Not Found"));

        room.setName(roomDetails.getName());
        room.setDescription(roomDetails.getDescription());
        room.setType(roomDetails.getType());
        room.setLocation(roomDetails.getLocation());
        room.setRent(roomDetails.getRent());
        room.setAvailable(roomDetails.isAvailable());

        Room updatedRoom = roomRepo.save(room);
        return mapToResponse(updatedRoom);
    }

    public void deleteRoom(Long id) {
        Room room = roomRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Room Not Found"));
        roomRepo.delete(room);
    }

    private RoomResponse mapToResponse(Room room) {
        return new RoomResponse(
                room.getId(),
                room.getName(),
                room.getDescription(),
                room.getType(),
                room.getLocation(),
                room.getRent(),
                room.isAvailable(),
                room.getOwner() != null ? room.getOwner().getName() : null,
                room.getOwner() != null ? room.getOwner().getEmail() : null
        );
    }
}
