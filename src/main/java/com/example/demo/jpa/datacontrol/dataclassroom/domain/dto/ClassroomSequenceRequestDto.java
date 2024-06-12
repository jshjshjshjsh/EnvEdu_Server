package com.example.demo.jpa.datacontrol.dataclassroom.domain.dto;

import com.example.demo.jpa.datacontrol.dataclassroom.domain.entity.ClassroomChapter;
import com.example.demo.jpa.datacontrol.dataclassroom.domain.entity.ClassroomClass;
import com.example.demo.jpa.datacontrol.dataclassroom.domain.entity.classroomsequencechunk.ClassroomSequenceChunk;
import lombok.Getter;

import java.util.List;

@Getter
public class ClassroomSequenceRequestDto {
    private ClassroomClass classroomClass = new ClassroomClass();
    private ClassroomChapter classroomChapter = new ClassroomChapter();
    private List<List<ClassroomSequenceChunk>> sequenceBlocks;
}
