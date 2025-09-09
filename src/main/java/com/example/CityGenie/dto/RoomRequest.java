package com.example.CityGenie.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class RoomRequest {

    @NotBlank(message = "Title is Required")
    private String title;

    private String description;

    @NotBlank
    private String type;

    @NotBlank(message = "Locaiton Is Required")
    private String location;

    @NotNull(message = "Rent is reuired")
    @Positive(message = "Rent Must Be positive")
    private Double rent;

    private boolean available;

}
