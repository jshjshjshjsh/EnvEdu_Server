package com.example.demo.datacontrol.dataclassroom.domain.entity;

import com.example.demo.user.model.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@NoArgsConstructor
public abstract class Classroom {
    // todo: Classroom을 Joined로 Inheritance 해서 하나로 관리하는건 아닌 것 같고 (각 Repository로 분리해야할 듯)
    //  대신 OneToMany로 양방향은 좋은 듯
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String subtitle;
    private String description;
    private LocalDateTime createTime = LocalDateTime.now();
    @ManyToOne
    @JsonIgnore
    private User owner;

    protected Classroom(String title, String subtitle, String description, User owner) {
        this.title = title;
        this.subtitle = subtitle;
        this.description = description;
        this.owner = owner;
    }
}
