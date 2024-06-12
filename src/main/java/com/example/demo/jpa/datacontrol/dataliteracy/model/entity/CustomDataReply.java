package com.example.demo.jpa.datacontrol.dataliteracy.model.entity;

import com.example.demo.jpa.user.model.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class CustomDataReply {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User owner;
    private Long classId;
    private Long chapterId;
    private Long sequenceId;
    private LocalDateTime latestTime;

    public void updateTitleAndContent(String title, String content){
        this.title = title;
        this.content = content;
        this.latestTime = LocalDateTime.now();
    }

    @Builder
    public CustomDataReply(String title, String content, User owner, Long classId, Long chapterId, Long sequenceId, LocalDateTime latestTime) {
        this.title = title;
        this.content = content;
        this.owner = owner;
        this.classId = classId;
        this.chapterId = chapterId;
        this.sequenceId = sequenceId;
        this.latestTime = latestTime;
    }
}
