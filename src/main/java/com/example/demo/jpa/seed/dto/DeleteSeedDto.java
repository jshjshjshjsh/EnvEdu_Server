package com.example.demo.jpa.seed.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class DeleteSeedDto {
    private Integer id;
    private List<String> sensors;
}
