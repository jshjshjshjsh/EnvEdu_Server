package com.example.demo.jpa.datacontrol.datafolder.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class DataFolder_DataCompilationDto {
    List<DataItems> data = new ArrayList<>();
}
