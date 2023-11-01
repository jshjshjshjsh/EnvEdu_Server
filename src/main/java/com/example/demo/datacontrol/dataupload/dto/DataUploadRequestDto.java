package com.example.demo.datacontrol.dataupload.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class DataUploadRequestDto {
    List<String> properties;
    List<List<String>> data;
    String label;
    String memo;
}
