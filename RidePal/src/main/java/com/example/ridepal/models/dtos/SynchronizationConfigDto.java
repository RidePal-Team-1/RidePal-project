package com.example.ridepal.models.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.NumberFormat;

@Getter
@Setter
public class SynchronizationConfigDto {

    @Min(value = 1, message = "Value must be greater than 0!")
    @Max(value = 1440, message = "Value must not exceed 1440!")
    private long interval;
}
