package com.example.CityGenie.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class RoomRequest {

    @NotBlank(message = "Name is Required")
    private String name;

    private String description;

    @NotBlank
    private String type;

    @NotBlank(message = "Location is required")
    private String location;

    @NotNull(message = "Rent is required")
    @Positive(message = "Rent Must Be positive")
    private Double rent;

    private boolean available;

}
