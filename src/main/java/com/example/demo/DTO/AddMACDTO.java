package com.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@Getter
public class AddMACDTO {
    @Pattern(regexp = "^[\\da-zA-Z][\\da-zA-Z]:[\\da-zA-Z][\\da-zA-Z]:[\\da-zA-Z][\\da-zA-Z]:[\\da-zA-Z][\\da-zA-Z]:[\\da-zA-Z][\\da-zA-Z]:[\\da-zA-Z][\\da-zA-Z]$")
    private String mac;
}
