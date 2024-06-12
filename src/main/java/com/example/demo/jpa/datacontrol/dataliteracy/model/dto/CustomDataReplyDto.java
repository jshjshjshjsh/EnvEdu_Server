package com.example.demo.jpa.datacontrol.dataliteracy.model.dto;

import com.example.demo.jpa.datacontrol.dataliteracy.model.entity.CustomDataReply;
import com.example.demo.jpa.user.model.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Getter
public class CustomDataReplyDto {

    private String title;
    private String content;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User owner;
    private Long classId;
    private Long chapterId;
    private Long sequenceId;
    private LocalDateTime latestTime = LocalDateTime.now();

    public void updateOwner(User owner){
        this.owner = owner;
    }

    public CustomDataReply toEntity(){
        return CustomDataReply.builder().title(title).content(content).owner(owner)
                .classId(classId).chapterId(chapterId).sequenceId(sequenceId).latestTime(latestTime).build();
    }
}
