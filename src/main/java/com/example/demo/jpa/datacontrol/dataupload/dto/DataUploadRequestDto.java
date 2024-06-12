package com.example.demo.jpa.datacontrol.dataupload.dto;

import com.example.demo.jpa.user.model.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
public class DataUploadRequestDto {
    private List<String> properties;
    private List<List<String>> data;
    private List<String> axisTypes;
    private String label;
    private String memo;
    private Long classId;
    private Long chapterId;
    private Long sequenceId;
    private UUID uuid;
    private LocalDateTime saveDate;
    private User owner;
    private Boolean canShared;
    private Boolean canSubmit;
}
