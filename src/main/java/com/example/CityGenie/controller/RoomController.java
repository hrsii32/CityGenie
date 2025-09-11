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

import com.example.CityGenie.dto.RoomRequest;
import com.example.CityGenie.dto.RoomResponse;
import com.example.CityGenie.entity.Room;
import com.example.CityGenie.entity.User;
import com.example.CityGenie.repository.UserRepository;
import com.example.CityGenie.service.RoomService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private UserRepository userRepo;

    @PreAuthorize("hasRole('PROVIDER, ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<RoomResponse> addRoom(@Valid @RequestBody RoomRequest request, Authentication auth) {
        String email = auth.getName(); // get logged-in user
        User owner = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        Room room = new Room();
        room.setName(request.getName());
        room.setDescription(request.getDescription());
        room.setType(request.getType());
        room.setLocation(request.getLocation());
        room.setRent(request.getRent());
        room.setAvailable(request.isAvailable());
        room.setOwner(owner);

        RoomResponse savedRoom = roomService.addRoom(room);
        return ResponseEntity.ok(savedRoom);
    }

    @GetMapping("/all")
    public ResponseEntity<List<RoomResponse>> getAllRooms() {
        return ResponseEntity.ok(roomService.getAllRooms());
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<RoomResponse>> getRoomByType(@PathVariable String type) {
        return ResponseEntity.ok(roomService.getRoomByType(type));
    }

    @GetMapping("/location")
    public ResponseEntity<List<RoomResponse>> getRoomByLocation(@RequestParam String location) {
        return ResponseEntity.ok(roomService.getRoomsByLocation(location));
    }

    @PreAuthorize("hasRole('OWNER')")
    @PutMapping("/update/{id}")
    public ResponseEntity<RoomResponse> updateRoom(@PathVariable Long id, @RequestBody RoomRequest request) {
        Room room = new Room();
        room.setName(request.getName());
        room.setDescription(request.getDescription());
        room.setType(request.getType());
        room.setLocation(request.getLocation());
        room.setRent(request.getRent());
        room.setAvailable(request.isAvailable());

        RoomResponse updatedRoom = roomService.updateRoom(id, room);
        return ResponseEntity.ok(updatedRoom);
    }

    @PreAuthorize("hasRole('OWNER')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return ResponseEntity.ok("Room deleted successfully");
    }
}
