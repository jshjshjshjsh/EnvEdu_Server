package com.example.demo.seed.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class DeleteSeedDto {
    private Integer id;
    private List<String> sensors;
}
