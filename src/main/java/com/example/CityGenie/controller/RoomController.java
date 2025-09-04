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

import com.example.CityGenie.entity.Room;
import com.example.CityGenie.service.RoomService;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @PostMapping("/add")
    public Room addRoom(@RequestBody Room room) {
        return roomService.addRoom(room);
    }

    @GetMapping("/all")
    public List<Room> getAllRoom() {
        return roomService.getAllRooms();
    }

    @GetMapping("/type/{type}")
    public List<Room> getRoomByType(@PathVariable String type) {
        return roomService.getRoomByType(type);
    }

    @GetMapping("/location")
    public List<Room> getRoomByLocation(@RequestParam String location) {
        return roomService.getRoomsByLocation(location);
    }

}
