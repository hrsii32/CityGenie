package com.example.CityGenie.dto;

import lombok.Data;

@Data
public class ServiceProviderResponse {

    private Long id;
    private String name;
    private String type;
    private String location;
    private boolean available;
    private String ownerName;
    private String ownerEmail;

    public ServiceProviderResponse(Long id, String name, String type,
            String location, boolean available, String ownerName, String ownerEmail) {

        this.id = id;
        this.name = name;
        this.type = type;
        this.location = location;
        this.available = available;
        this.ownerName = ownerName;
        this.ownerEmail = ownerEmail;

    }

}
