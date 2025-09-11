package com.example.CityGenie.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ServiceProviderRequest {

    @NotBlank(message = "Name is Required")
    private String name;

    @NotBlank(message = "Type is required")
    private String type;

    @NotBlank(message = "location is Required")
    private String location;

    private boolean available;

}
