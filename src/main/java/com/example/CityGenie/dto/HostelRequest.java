package com.example.CityGenie.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class HostelRequest {

    @NotBlank(message = "Name is requires")
    private String name;

    @NotBlank(message = "location is required")
    private String location;

    @NotBlank(message = "type is required")
    private String type;

    @NotNull(message = "Rent is Requird")
    @Positive(message = "Rent Per Month be positive")
    private Double rent;

    private boolean available;

}
