package com.example.demo.datacontrol.datafolder.dto;

import com.example.demo.datacontrol.datachunk.model.parent.DataSuperTypes;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class DataFolder_DataCompilationDto {
    List<DataSuperTypes> data = new ArrayList<>();
}
