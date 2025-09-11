package com.example.CityGenie.dto;

import lombok.Data;

@Data
public class RoomResponse {

    private Long id;
    private String name;
    private String description;
    private String type;
    private String location;
    private Double rent;
    private boolean available;
    private String ownerName;
    private String ownerEmail;

    public RoomResponse(Long id, String name, String description, String type, String location,
            Double rent, boolean available, String ownerName, String ownerEmail) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.location = location;
        this.rent = rent;
        this.available = available;
        this.ownerName = ownerName;
        this.ownerEmail = ownerEmail;

    }

}
