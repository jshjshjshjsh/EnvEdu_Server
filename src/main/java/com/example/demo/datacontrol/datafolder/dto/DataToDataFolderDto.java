package com.example.demo.datacontrol.datafolder.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class DataToDataFolderDto {
    private Long folderId;
    private List<Long> dataId;
    private String label;
}
