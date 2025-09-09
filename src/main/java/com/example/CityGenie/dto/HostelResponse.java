package com.example.CityGenie.dto;

import lombok.Data;

@Data
public class HostelResponse {

    private Long id;
    private String name;
    private String location;
    private String type;
    private Double rent;
    private boolean available;
    private String ownerName;
    private String ownerEmail;

    public HostelResponse(Long id, String name, String location, String type,
            Double rent, boolean available, String ownerName, String ownerEmail) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.type = type;
        this.rent = rent;
        this.available = available;
        this.ownerName = ownerName;
        this.ownerEmail = ownerEmail;
    }

}
