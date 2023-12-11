package com.example.demo.datacontrol.dataliteracy.model.dto;

import com.example.demo.datacontrol.dataliteracy.model.entity.CustomData;
import com.example.demo.user.model.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class CustomDataDto {
    private List<String> properties = new ArrayList<>();
    private List<List<String>> data = new ArrayList<>();
    private String memo;
    @Column(columnDefinition = "BINARY(16)")
    private UUID uuid;
    private LocalDateTime saveDate;
    @JsonIgnore
    private User owner;
    private Long classId;
    private Long chapterId;
    private Long sequenceId;
    private Boolean isSubmit;

    public void updateOwner(User owner){
        this.owner = owner;
    }

    public CustomDataDto convertCustomDataToDto(List<CustomData> customDataList){
        CustomData firstCustomData = customDataList.get(0);
        String[] items = firstCustomData.getProperties().split(", ");
        this.properties.addAll(Arrays.asList(items));
        this.memo = firstCustomData.getMemo();
        this.uuid = firstCustomData.getUuid();
        this.saveDate = firstCustomData.getSaveDate();

        for (CustomData customData : customDataList) {
            String[] customDataData = customData.getData().split(", ");
            this.data.add(new ArrayList<>(Arrays.asList(customDataData)));
        }

        return this;
    }

    public List<CustomData> convertDtoToEntity(){
        List<CustomData> customData = new ArrayList<>();
        UUID uuid = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();
        if(isSubmit == null)
            isSubmit = false;

        String properties = "";
        for (String property : this.properties) {
            properties += property + ", ";
        }
        properties = properties.substring(0, properties.length() - 2);

        for (List<String> data: this.data) {
            String record = "";
            for (String value : data) {
                record += value + ", ";
            }
            record = record.substring(0, record.length() - 2);

            customData.add(new CustomData(properties, record, memo, uuid, now, owner, classId, chapterId, sequenceId, isSubmit));
        }


        return customData;
    }

    public CustomDataDto(List<String> properties, List<List<String>> data, String memo, User owner, Long classId, Long chapterId, Long sequenceId, Boolean isSubmit) {
        this.properties = properties;
        this.data = data;
        this.memo = memo;
        this.owner = owner;
        this.classId = classId;
        this.chapterId = chapterId;
        this.sequenceId = sequenceId;
        this.isSubmit = isSubmit;
    }
}
