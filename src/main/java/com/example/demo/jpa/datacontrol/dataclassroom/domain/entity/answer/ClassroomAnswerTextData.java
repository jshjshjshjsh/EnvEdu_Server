package com.example.demo.jpa.datacontrol.dataclassroom.domain.entity.answer;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@EntityListeners(ClassroomDataListener.class)
@NoArgsConstructor
public class ClassroomAnswerTextData extends ClassroomData {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    @Enumerated(EnumType.STRING)
    private ClassroomAnswerDataType answerType;


}
