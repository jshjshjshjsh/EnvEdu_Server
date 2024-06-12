package com.example.demo.jpa.datacontrol.dataliteracy.model.dto;

import lombok.Getter;

@Getter
public class DataLiteracyDatasetDto extends CustomDataDto{

    private String title;
    private String content;
    private String gradeLabel;
    private String subjectLabel;
    private String dataTypeLabel;
}
