package com.example.demo.datacontrol.dataclassroom.domain.entity.answer;

import com.example.demo.user.model.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@Getter
@MappedSuperclass
@NoArgsConstructor
public abstract class ClassroomData {

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User owner;
    @Transient
    private String username;
    private Long classId;
    private Long chapterId;
    private Long sequenceId;
    private Boolean canShare = false;
    private Boolean canSubmit = false;

    public void updateUsername(){
        this.username = owner.getUsername();
    }

    public void updateOwner(User owner){
        this.owner = owner;
    }

    public void updateShareAndSubmit(Boolean canShare, Boolean canSubmit){
        this.canShare = canShare;
        this.canSubmit = canSubmit;
    }
}
